package com.leverx.db;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:dbPropertiesConfiguration.properties")
@Getter
public class PostgreDBPropertiesConfiguration implements DBPropertiesConfiguration {
    @Value("${db.url}")
    private String URL;
    @Value("${db.user}")
    private String USER;
    @Value("${db.password}")
    private String PASSWORD;
    @Value("${db.driverClass}")
    private String DRIVER_CLASS;
}
