package io.github.ganchix.versioning.configuration;

import io.github.ganchix.versioning.domain.PathVersionMap;
import io.github.ganchix.versioning.domain.Version;
import io.github.ganchix.versioning.domain.VersionRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to matched a request with header accept,
 * and build the conditions to match.
 * <p>
 * Created by Rafa on 24/01/17.
 */

public class VersionRequestCondition extends AbstractRequestCondition<VersionRequestCondition> {
    private final VersionRange version;
    private final String acceptedMediaType;
    private final String path;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public VersionRequestCondition(String acceptedMediaType, String from, String to, String path) {
        this(acceptedMediaType, versionRange(from, to), path);
    }

    public VersionRequestCondition(String acceptedMediaType, VersionRange version, String path) {
        PathVersionMap.INSTANCE.addPathWithVersion(path, version);

        this.path = path;
        this.acceptedMediaType = acceptedMediaType;
        this.version = version;
    }

    private static VersionRange versionRange(String from, String to) {

        if (StringUtils.hasText(from)) {
            String toVersion = (StringUtils.hasText(to) ? to : Version.MAX_VERSION);
            return new VersionRange(from, toVersion);

        }

        return null;
    }

    @Override
    public VersionRequestCondition combine(VersionRequestCondition other) {
        logger.debug("Combining:\n{}\n{}", this, other);
        String newMediaType;
        if (StringUtils.hasText(this.acceptedMediaType) && StringUtils.hasText(other.acceptedMediaType)
                && !this.acceptedMediaType.equals(other.acceptedMediaType)) {
            throw new IllegalArgumentException("Both conditions should have the same media type. " + this.acceptedMediaType + " =!= " + other.acceptedMediaType);
        } else if (StringUtils.hasText(this.acceptedMediaType)) {
            newMediaType = this.acceptedMediaType;
        } else {
            newMediaType = other.acceptedMediaType;
        }
        if (StringUtils.isEmpty(newMediaType)) {
            throw new IllegalArgumentException("Accept media type must be defined");
        }

        VersionRange version = other.version;
        if (this.version != null) {
            version = this.version;
        }
        return new VersionRequestCondition(newMediaType, version, other.path);
    }

    @Override
    public VersionRequestCondition getMatchingCondition(HttpServletRequest request) {

        String accept = request.getHeader("Accept");
        if (!StringUtils.isEmpty(accept) && this.version != null) {
            Pattern regexPattern = Pattern.compile("(.*)-(v\\d+\\.\\d+).*");
            Matcher matcher = regexPattern.matcher(accept);
            if (matcher.matches()) {
                String actualMediaType = matcher.group(1);
                String version = matcher.group(2);
                logger.debug("Version={}", version);

                if (acceptedMediaType.startsWith(actualMediaType)) {

                    if (this.version.includes(version)) {
                        return this;
                    }

                }
            }
        }
        logger.debug("Didn't find matching version");
        return null;
    }

    @Override
    public int compareTo(VersionRequestCondition other, HttpServletRequest request) {
        return 0;
    }

    @Override
    protected Collection<?> getContent() {
        return Arrays.asList(
                "media=".concat(acceptedMediaType),
                version == null ? "" : version.toString(),
                "path=".concat(path));
    }

    @Override
    protected String getToStringInfix() {
        return " && ";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionRequestCondition)) return false;
        if (!super.equals(o)) return false;

        VersionRequestCondition that = (VersionRequestCondition) o;

        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (acceptedMediaType != null ? !acceptedMediaType.equals(that.acceptedMediaType) : that.acceptedMediaType != null) return false;
        return path != null ? path.equals(that.path) : that.path == null;
    }


}
