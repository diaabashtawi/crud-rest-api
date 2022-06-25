package com.emp.config;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.emp.config")
@PropertySource({"classpath:persistence-mysql.properties"})
public class MainAppConfig {

    @Autowired
    private Environment environment;

    private Logger logger = Logger.getLogger(getClass().getName());

    // Define Bean for ViewResolver
    @Bean
    public DataSource dataSource(){

        // Create Connection Pool
        ComboPooledDataSource dataSource= new ComboPooledDataSource();

        // Set the JDBC Driver
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        }catch (PropertyVetoException exc){
            throw new RuntimeException(exc);
        }


        logger.info("jdbc.url= " + environment.getProperty("jdbc.url"));
        logger.info("jdbc.user= " + environment.getProperty("jdbc.user"));

        // Set database connection props
        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        dataSource.setUser(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));

        // Set connection pool props
        dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return dataSource;
    }

    private Properties getHibernatePropertise(){
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));

        return properties;

    }

    // Need a helper method
    // Read environment property and convert to int
    private int getIntProperty(String propName){
        String propValue = environment.getProperty(propName);

        // Convert to Integer
        int intPropValue = Integer.parseInt(propValue);

        return intPropValue;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){

        // Create Session Factory object
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        // Set THe Properties
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
        sessionFactoryBean.setHibernateProperties(getHibernatePropertise());

        return sessionFactoryBean;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager (SessionFactory sessionFactory){

        // Setup Transaction Manager based on session Factory
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();

        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }
}
