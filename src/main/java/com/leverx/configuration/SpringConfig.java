package com.leverx.configuration;

import com.leverx.db.DBPropertiesConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("com.leverx")
@EnableWebMvc
@EnableTransactionManagement
@Slf4j
public class SpringConfig implements WebMvcConfigurer {
    private final DBPropertiesConfiguration dbPropertiesConfiguration;

    @Autowired
    public SpringConfig(DBPropertiesConfiguration dbPropertiesConfiguration) {
        this.dbPropertiesConfiguration = dbPropertiesConfiguration;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.leverx.entity");

        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(dbPropertiesConfiguration.getDRIVER_CLASS());
            dataSource.setJdbcUrl(dbPropertiesConfiguration.getURL());
            dataSource.setUser(dbPropertiesConfiguration.getUSER());
            dataSource.setPassword(dbPropertiesConfiguration.getPASSWORD());
            dataSource.setInitialPoolSize(10);
            dataSource.setMaxPoolSize(20);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            log.error("Exception occurred while trying to initialize dataSource bean: {}", e.toString());
        }
        return dataSource;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();

        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQL9Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("format_sql", "true");
        hibernateProperties.setProperty("use_sql_comments", "true");

        return hibernateProperties;
    }

}
