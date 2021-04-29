package com.leverx.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("com.leverx")
@PropertySource("classpath:dbPropertiesConfiguration.properties")
@EnableWebMvc
@EnableJpaRepositories(value = "com.leverx.repository", bootstrapMode = BootstrapMode.DEFERRED)
@EnableTransactionManagement
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class SpringConfig implements WebMvcConfigurer {
    private final Environment environment;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
        config.setJdbcUrl(Objects.requireNonNull(environment.getProperty("spring.datasource.url")));
        config.setUsername(Objects.requireNonNull(environment.getProperty("spring.datasource.username")));
        config.setPassword(Objects.requireNonNull(environment.getProperty("spring.datasource.password")));
        config.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty("cp.maxPoolSize"))));

        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("com.leverx.entity");
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
