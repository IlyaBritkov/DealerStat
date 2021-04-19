package com.leverx.db;

import com.leverx.util.PropertyReader;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Getter
public class PostgreDBPropertiesConfiguration implements DBPropertiesConfiguration {
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final String DRIVER_CLASS;

    private PostgreDBPropertiesConfiguration() {
        this.URL = Objects.requireNonNull(PropertyReader.getProperties().getProperty("url"));
        this.USER = Objects.requireNonNull(PropertyReader.getProperties().getProperty("user"));
        this.PASSWORD = Objects.requireNonNull(PropertyReader.getProperties().getProperty("password"));
        this.DRIVER_CLASS = Objects.requireNonNull(PropertyReader.getProperties().getProperty("driverClass"));
    }
}
