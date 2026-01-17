package dev.restfulApi.config;

import dev.restfulApi.config.enums.Environment;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    private static AppConfig instance;

    private final Environment environment;

    private AppConfig(Environment environment, Properties props) {
        this.environment = environment;
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = loadInternal();
                }
            }
        }
        return instance;
    }

    private static AppConfig loadInternal() {
        String envName = System.getProperty("env");
        if (envName == null || envName.isBlank()) {
            envName = System.getenv("ENV");
        }

        Environment env = Environment.fromString(envName);
        String fileName = "config-" + env.getName() + ".properties";

        Properties props = new Properties();
        try (var in = AppConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new RuntimeException("Config file not found: " + fileName);
            }
            props.load(in);
            return new AppConfig(env, props);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + fileName, e);
        }
    }

    public Environment getEnvironment() { return environment; }
}
