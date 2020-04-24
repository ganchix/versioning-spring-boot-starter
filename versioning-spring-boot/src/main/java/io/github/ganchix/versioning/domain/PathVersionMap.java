package io.github.ganchix.versioning.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton used for detect collision between versions.
 *
 * Created by Rafa on 25/01/17.
 */
public enum PathVersionMap {
    INSTANCE;
    private final Map<String, VersionRange> pathWithVersion = new ConcurrentHashMap();

    public void addPathWithVersion(String path, VersionRange version) {

        if (path != null && version != null) {
            pathWithVersion.put(path, version);
        }
    }

    public void checkColisionPathWithVersion(String path, String... versions) {
        if (path != null) {
            VersionRange versionsToCheck = pathWithVersion.get(path);
            if (versionsToCheck != null) {
                for (String version : versions) {

                    if (versionsToCheck.includes(version)) {
                        throw new RuntimeException("Exists collision between version, path " + path + ", contains version " + version + " and " + versionsToCheck);
                    }

                }


            }
        }

    }

}
