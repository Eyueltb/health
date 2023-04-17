package com.health.dto;

import lombok.Data;
/**
 * Q- Implement Version class and test all necessary rules
 *  version in the format X.Y.Z, where:
 *
 *  	X stands for major,
 *  	Y stands for minor.
 *  	Z stands for patch.
 *  regex = "\\d+(\\.\\d+){0,2}(-SNAPSHOT)?";
 *      Starts with one or more digits (\d+), Followed by . and one or more digits but optional
 *      group can occur 0 to 2 times, allowing for up to two dot-separated numeric segments.
 *      Ends with an optional group that consists of a hyphen followed by "SNAPSHOT" (-SNAPSHOT)?
 *      with zero or 1
 *
 * Rules
 * On constructor, for version string
 * a. when version is null => throw IllegalArgumentException("'version' must not be null!")
 * b. when version doesn't match regex => throw IllegalArgumentException("'version' must match: 'major.minor.patch(-SNAPSHOT)'!");
 * c. When version is correct => no exception is thrown split
 *
 * Versions comparison
 * i. When is version == null => throw IllegalArgumentException("'other' must not be null!")
 * ii.  The snapshot version must always be lower than any non-snapshot
 * iii. Two given versions,in terms of integer comparisons,
 *      the one containing this.x >= other.x; * or, this.y >= other.y; or,this.z  >=  other.z
 *    If x, y, z are all equal for two given versions(v1, v2) => v1 and v2 are considered equal.
 */
@Data
public class Version implements Comparable<Version> {
    private int major;
    private int minor;
    private int patch;
    private boolean isSnapshot;

    public Version(String version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException(UseExceptionType.VERSION_MUST_NOT_BE_NULL.getErrorMessage());
        }
        String regex = "\\d+(\\.\\d+){0,2}(-SNAPSHOT)?";
        if (!version.matches(regex)) {
            throw new IllegalArgumentException(UseExceptionType.VERSION_MUST_MATCH.getErrorMessage());
        }

        String[] parts = version.split("\\.");
        major = Integer.parseInt(parts[0]);
        minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        patch = parts.length > 2 ? Integer.parseInt(parts[2].split("-")[0]) : 0;
        isSnapshot = version.endsWith("-SNAPSHOT");

    }

    @Override
    public int compareTo(Version other) {
        if (other == null) {
            throw new IllegalArgumentException(UseExceptionType.OTHER_MUST_NOT_BE_NULL.getErrorMessage());
        }
        if (this.isSnapshot() && !other.isSnapshot()) {  return -1; }
        else if (!this.isSnapshot() && other.isSnapshot()) { return 1; }

        int majorComparison = Integer.compare(this.major, other.major);
        if (majorComparison != 0) { return majorComparison; }

        int minorComparison = Integer.compare(this.minor, other.minor);
        if (minorComparison != 0) { return minorComparison; }

        int patchComparison = Integer.compare(this.patch, other.patch);
        if (patchComparison != 0) { return patchComparison; }

        return 0;

    }

    public enum UseExceptionType {
        VERSION_MUST_NOT_BE_NULL("'version' must not be null!"),
        VERSION_MUST_MATCH("'version' must match: 'major.minor.patch(-SNAPSHOT)'!"),
        OTHER_MUST_NOT_BE_NULL("'other' must not be null!"),
        OTHER_VERSION_MUST_NOT_MATCH("'other' must not be null!");

        private final String errorMessage;

        UseExceptionType(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

}
