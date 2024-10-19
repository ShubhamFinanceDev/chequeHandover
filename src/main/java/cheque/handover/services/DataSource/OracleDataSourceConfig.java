package cheque.handover.services.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class OracleDataSourceConfig {

    @Value("${oracale.datasource.url}")
    private String url;

    @Value("${oracale.datasource.username}")
    private String username;

    @Value("${oracale.datasource.password}")
    private String password;

    @Value("${oracale.datasource.driver-class-name}")
    private String driverClassName;

    @Bean(name = "oracleJdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
    }
}
