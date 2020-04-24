package io.github.ganchix.versioning;

import io.github.ganchix.versioning.configuration.VersionRequestCondition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

/**
 * Created by Rafael Ríos on 3/04/17.
 */
@RunWith(JUnit4.class)
public class VersionRequestConditionTest {
    private VersionRequestCondition versionRequestCondition;
    private VersionRequestCondition versionRequestConditionNullMediaType;
    private VersionRequestCondition versionRequestConditionNullVersion;

    @Before
    public void init() {
        versionRequestCondition = new VersionRequestCondition("acceptedMediaType", "1.0", "2.0", "path");
        versionRequestConditionNullMediaType = new VersionRequestCondition("", "1.0", "2.0", "path");
        versionRequestConditionNullVersion = new VersionRequestCondition("acceptedMediaType", null, null, "path");

    }

    @Test
    public void testMatchingConditionWithNoMatch_shouldReturnNull() {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("Accept", "acceptedMediaType-v3.0+json");
        VersionRequestCondition matchingCondition = versionRequestCondition.getMatchingCondition(httpServletRequest);
        assertNull(matchingCondition);
    }

    @Test
    public void testMatchingConditionWitMatch_shouldReturnMatching() {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("Accept", "acceptedMediaType-v2.0+json");
        VersionRequestCondition matchingCondition = versionRequestCondition.getMatchingCondition(httpServletRequest);
        assertThat(versionRequestCondition, is(matchingCondition));

    }

    @Test
    public void testToString_shouldThePrintElement() {
        assertThat(versionRequestCondition.toString(), is("[media=acceptedMediaType && range[v1.0-v2.0] && path=path]"));
    }

    @Test
    public void testCompare_shouldReturnZero() {
        assertThat(versionRequestCondition.compareTo(null, null), is(0));
    }

    @Test(expected = Exception.class)
    public void testCombineNullMediaType_shouldReturnAException() {
        versionRequestConditionNullMediaType.combine(versionRequestConditionNullMediaType);
    }

    @Test
    public void testCombineWithDiferentMediaType_shouldReturnAConditionWithNewMediaType() {
        VersionRequestCondition resultCondition = versionRequestConditionNullMediaType.combine(versionRequestCondition);
        assertThat(resultCondition, is(versionRequestCondition));
    }

    @Test
    public void testCombineWithNullVersion_shouldReturnAConditionWithNewVersion() {
        VersionRequestCondition resultCondition = versionRequestConditionNullVersion.combine(versionRequestCondition);
        assertThat(resultCondition, is(versionRequestCondition));
    }

    @Test
    public void testCombineWithNullToVersion_shouldReturnAConditionWith99dot99Version() {
        VersionRequestCondition versionRequestCondition = new VersionRequestCondition("acceptedMediaType", "1.0", null, "path");
        VersionRequestCondition versionRequestConditionWithTo = new VersionRequestCondition("acceptedMediaType", "1.0", "99.99", "path");
        assertThat(versionRequestCondition, is(versionRequestConditionWithTo));

    }


}
