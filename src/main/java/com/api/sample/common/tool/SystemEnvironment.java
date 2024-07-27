package com.api.sample.common.tool;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SystemEnvironment {
    private static final String SYSTEM_OS = System.getProperty("os.name").toLowerCase();

    public boolean isWindows() {
        return SYSTEM_OS.contains("win");
    }
    public boolean isLinux() {
        return SYSTEM_OS.contains("linux");
    }
}
