package com.leverx.configuration.dbConfiguration;

import com.leverx.util.PropertyReader;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Getter
public class PostgreDBConfiguration implements DBConfiguration {
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    private PostgreDBConfiguration() {
        this.URL = Objects.requireNonNull(PropertyReader.getProperties().getProperty("url"));
        this.USER = Objects.requireNonNull(PropertyReader.getProperties().getProperty("user"));
        this.PASSWORD = Objects.requireNonNull(PropertyReader.getProperties().getProperty("password"));
    }
}
