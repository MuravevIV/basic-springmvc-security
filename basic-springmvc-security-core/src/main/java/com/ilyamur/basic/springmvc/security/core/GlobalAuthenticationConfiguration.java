package com.ilyamur.basic.springmvc.security.core;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalAuthentication
public class GlobalAuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //
        // Better use auth.jdbcAuthentication() or something like it in production environment.
        // Passwords are encrypted with BCryptPasswordEncoder and not stored in-memory as "admin" and "user".
        // They are encoded here directly from string values for demo purposes only - and
        // !!!
        // you should never do this in real projects!
        // !!!
        //
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("admin").password(passwordEncoder.encode("admin")).roles("SITE_ADMIN").and()
                .withUser("user").password(passwordEncoder.encode("user")).roles("REGISTERED_USER");
    }
}
