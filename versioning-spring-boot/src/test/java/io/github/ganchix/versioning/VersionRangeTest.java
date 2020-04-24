package io.github.ganchix.versioning;

import io.github.ganchix.versioning.domain.VersionRange;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;

/**
 * Created by Rafael Ríos on 2/04/17.
 */
@RunWith(JUnit4.class)
public class VersionRangeTest {

    @Test(expected = Exception.class)
    public void testEmptyFromVersion_ShouldReturnException() {
        new VersionRange(null, "1.0");
    }

    @Test(expected = Exception.class)
    public void testFromLessThanTo_ShouldReturnException() {
        new VersionRange("2.0", "1.0");

    }

    @Test
    public void testCompareNotInclusiveRanges_ShouldReturnFalse() {
        VersionRange versionRange = new VersionRange("1.0", "2.0");

        assertFalse(versionRange.includes("4.0"));

    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(VersionRange.class)
                .verify();
    }

}
