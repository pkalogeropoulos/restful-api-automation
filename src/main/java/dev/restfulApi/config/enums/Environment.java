package dev.restfulApi.config.enums;

import org.apache.commons.lang3.StringUtils;

public enum Environment {
    DEV("dev"),
    QA("qa"),
    STAGING("staging"),
    PRODUCTION("production");


    private final String name;

    Environment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Environment fromString(String env) {
        if (StringUtils.isAllBlank(env)) {
            return DEV;
        }
        env = env.trim().toLowerCase();
        for (Environment e : values()) {
            if (e.name.equalsIgnoreCase(env)) {
                return e;
            }
        }
        return PRODUCTION; // fallback
    }
}
