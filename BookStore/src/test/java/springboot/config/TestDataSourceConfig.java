package springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.context.annotation.Bean;


import javax.sql.DataSource;

@TestConfiguration
public class TestDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix="security.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

}
