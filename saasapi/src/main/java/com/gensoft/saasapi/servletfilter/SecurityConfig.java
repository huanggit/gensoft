package com.gensoft.saasapi.servletfilter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

import javax.sql.DataSource;

/**
 * Created by alan on 16-5-23.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, status FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, enabled FROM users WHERE username=?");

    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/user/register").permitAll()
//                .antMatchers("/visitor/**").access("hasRole('OWNER') or hasRole('VISITOR')")
//                .anyRequest().hasRole("USER")
                .anyRequest().permitAll()//.authenticated()
                .and().formLogin()
                    //.loginPage("/user/login")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/user/login")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                .and().sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
        ;
    }
}
