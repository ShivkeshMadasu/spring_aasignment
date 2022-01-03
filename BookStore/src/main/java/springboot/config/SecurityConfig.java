package springboot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // add a reference to our security data source

    @Autowired
    @Qualifier("securityDataSource")
    private DataSource securityDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // use jdbc authentication
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.jdbcAuthentication().passwordEncoder(encoder).dataSource(securityDataSource);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String employee = "EMPLOYEE";
        String admin = "ADMIN";

        http.authorizeRequests()
                .antMatchers("/").hasAnyRole(employee,admin)
                .antMatchers("/*/list").hasAnyRole(employee,admin)
                .antMatchers("/*/add").hasAnyRole(employee,admin)
                .antMatchers("/*/save").hasAnyRole(employee,admin)
                .antMatchers("/*/update").hasAnyRole(employee,admin)
                .antMatchers("/*/delete").hasRole(admin)
                .antMatchers("/customers/update*").hasAnyRole(employee,admin)

                .antMatchers("/resources/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

    }

}



