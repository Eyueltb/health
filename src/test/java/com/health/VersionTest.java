package com.health;

import com.health.dto.Version;
import org.junit.Test;


import static org.assertj.core.api.Assertions.*;

/**
 * package com.codility;
 * <p>
 * import org.junit.Assert;
 * import org.junit.Test;
 * <p>
 * import static org.assertj.core.api.Assertions.*;
 * <p>
 * public class VersionTest {
 * // ... write your unit tests here ...
 *
 * @Test public void exampleTest() {
 * Version version = new Version("3.8.0");
 * // ...
 * }
 * @Test public void exampleTest2() {
 * Version version = new Version("3.8.0-SNAPSHOT");
 * // ...
 * }
 * <p>
 * // expected error messages:
 * <p>
 * static final String errorVersionMustNotBeNull = "'version' must not be null!";
 * static final String errorOtherMustNotBeNull =  "'other' must not be null!";
 * static final String errorVersionMustMatchPattern =  "'version' must match: 'major.minor.patch(-SNAPSHOT)'!";
 * }
 */


public class VersionTest {
    @Test
    public void testConstructorWithValidVersion() {
        Version version = new Version("1.2.3");
        assertThat(version.isSnapshot()).isFalse();
        assertThat(version.compareTo(new Version("1.2.3"))).isEqualTo(0);
    }

    @Test
    public void testConstructorWithValidSnapshotVersion() {
        Version version = new Version("1.2.3-SNAPSHOT");
        assertThat(version.isSnapshot()).isTrue();
        assertThat(version.compareTo(new Version("1.2.3-SNAPSHOT"))).isEqualTo(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullVersion() {
        new Version(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidVersion() {
        new Version("1.2.3.4");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidSnapshotVersion() {
        new Version("1.2.3-snapshot");
    }

    @Test
    public void testCompareToWithNullOtherVersion() {
        Version version = new Version("1.2.3");
        assertThatThrownBy(() -> version.compareTo(null))
                .isInstanceOf(IllegalArgumentException.class) //.hasMessage(errorOtherMustNotBeNull);
                .hasMessage(UseExceptionType.ERROR_OTHER_MUST_NOT_BE_NULL.getErrorMessage());
    }

    @Test
    public void testConstructorWithNullArgument() {
        assertThatThrownBy(() -> new Version(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(UseExceptionType.ERROR_VERSION_MUST_NOT_BE_NULL.getErrorMessage());//errorVersionMustNotBeNull
    }

    @Test
    public void testConstructorWithInvalidArgument() {
        assertThatThrownBy(() -> new Version("1.2.3.4"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(UseExceptionType.ERROR_VERSION_MUST_MATCH.getErrorMessage());//errorVersionMustMatchPattern
    }

    @Test
    public void testCompareToWithSnapshotAndNonSnapshotVersions() {
        Version v1 = new Version("1.2.3");

        assertThat(v1.getMajor()).isEqualTo(1);
        assertThat(v1.getMinor()).isEqualTo(2);
        assertThat(v1.getPatch()).isEqualTo(3);
        assertThat(v1.isSnapshot()).isFalse();

        Version v2 = new Version("1.2.3-SNAPSHOT");
        assertThat(v2.isSnapshot()).isTrue();

        assertThat(v1.compareTo(v1)).isEqualTo(0);
        assertThat(v1.compareTo(v2)).isEqualTo(1);
        assertThat(v2.compareTo(v1)).isEqualTo(-1);

    }

    @Test
    public void testCompareToWithDifferentVersions() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.4");
        Version v3 = new Version("1.1.3");
        Version v4 = new Version("2.0.0");
        Version v5 = new Version("1.2.3-SNAPSHOT");

        assertThat(v1.compareTo(v1)).isEqualTo(0);
        assertThat(v1.compareTo(v2)).isLessThan(0);
        assertThat(v2.compareTo(v1)).isGreaterThan(0);

        assertThat(v2.compareTo(v3)).isGreaterThan(0);
        assertThat(v3.compareTo(v2)).isLessThan(0);
        assertThat(v3.compareTo(v4)).isLessThan(0);
        assertThat(v4.compareTo(v3)).isGreaterThan(0);
        assertThat(v1.compareTo(v5)).isGreaterThan(0);
        assertThat(v5.compareTo(v1)).isLessThan(0);
    }

    public enum UseExceptionType {
        ERROR_VERSION_MUST_NOT_BE_NULL("'version' must not be null!"),
        ERROR_OTHER_MUST_NOT_BE_NULL("'other' must not be null!"),
        ERROR_VERSION_MUST_MATCH("'version' must match: 'major.minor.patch(-SNAPSHOT)'!");

        private final String errorMessage;

        UseExceptionType(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
