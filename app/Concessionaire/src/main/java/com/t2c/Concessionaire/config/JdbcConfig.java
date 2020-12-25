package com.t2c.Concessionaire.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

@Configuration
@ComponentScan("com.t2c.Concessionaire")
public class JdbcConfig {
    @Bean
    @Primary
    public HikariDataSource dataSource() {
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "cars");
        props.setProperty("dataSource.password", "cars");
        props.setProperty("dataSource.databaseName", "concessionaire");
        HikariConfig config = new HikariConfig(props);
        return new HikariDataSource(config);
    }
}