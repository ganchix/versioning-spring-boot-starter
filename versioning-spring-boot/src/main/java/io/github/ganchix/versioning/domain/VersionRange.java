package io.github.ganchix.versioning.domain;


import org.springframework.util.StringUtils;

/**
 * Indicates the version range.
 *
 * Created by Rafa on 24/01/17.
 */
public class VersionRange {


    private final Version from;
    private final Version to;

    public VersionRange(String from, String to) {
        if (StringUtils.isEmpty(from)) {
            throw new IllegalArgumentException("Define from in @VersionedResource");
        }
        this.from = new Version(from);
        this.to = new Version(to);
        if (this.from.compareTo(this.to) != -1 && this.from.compareTo(this.to) != 0) {
            throw new IllegalArgumentException("From is minor than to, bad definition");
        }
    }

    public boolean includes(String other) {
        Version otherVersion = new Version(other);

        if (from.compareTo(otherVersion) <= 0 && to.compareTo(otherVersion) >= 0) {
            return true;
        }

        return false;
    }


    @Override
    public String toString() {
        return "range[" + from + "-" + to + "]";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionRange)) return false;

        VersionRange that = (VersionRange) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        return to != null ? to.equals(that.to) : that.to == null;
    }

    @Override
    public final int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
