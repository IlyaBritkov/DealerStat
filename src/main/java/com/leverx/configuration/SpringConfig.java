package com.leverx.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("com.leverx")
@PropertySource("classpath:dbPropertiesConfiguration.properties")
@EnableWebMvc
@EnableJpaRepositories("com.leverx.repository")
@EnableTransactionManagement
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class SpringConfig implements WebMvcConfigurer {
    private final Environment environment;

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
            dataSource.setJdbcUrl(Objects.requireNonNull(environment.getProperty("spring.datasource.url")));
            dataSource.setUser(Objects.requireNonNull(environment.getProperty("spring.datasource.username")));
            dataSource.setPassword(Objects.requireNonNull(environment.getProperty("spring.datasource.password")));
            dataSource.setInitialPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty("cp.initialPoolSize"))));
            dataSource.setMaxPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty("cp.maxPoolSize"))));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            log.error("Exception occurred while trying to initialize dataSource bean: {}", e.toString());
        }
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan(new String[]{"com.leverx.entity"});

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(hibernateProperties());

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect",
                Objects.requireNonNull(environment.getProperty("hibernate.dialect")));
        hibernateProperties.setProperty("hibernate.show_sql",
                Objects.requireNonNull(environment.getProperty("hibernate.show_sql")));
        hibernateProperties.setProperty("format_sql",
                Objects.requireNonNull(environment.getProperty("hibernate.format_sql")));
        hibernateProperties.setProperty("use_sql_comments",
                Objects.requireNonNull(environment.getProperty("hibernate.use_sql_comments")));

        return hibernateProperties;
    }
}
