package com.ilyamur.basic.springmvc.security.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Responsible for all application's security features.
 * Included as root config class in WebApplicationInitializer.
 */
@EnableWebSecurity
@Import(GlobalAuthenticationConfiguration.class)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final int TWO_WEEKS_IN_SEC = 2 * 7 * 24 * 60 * 60;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // http://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html#authorize-requests
                .antMatchers("/admin_console*").hasRole("SITE_ADMIN").and()
                // http://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html#jc-form
                .formLogin().loginPage("/login").permitAll().and().logout().permitAll().and()
                // http://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf().csrfTokenRepository(csrfTokenRepository()).and()
                // http://docs.spring.io/spring-security/site/docs/current/reference/html/ns-config.html#ns-remember-me
                .rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(TWO_WEEKS_IN_SEC);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // Should be replaced with JdbcTokenRepositoryImpl or custom PersistentTokenRepository in production environment.
        // Because it's in-memory and not persistent at all.
        return new InMemoryTokenRepositoryImpl();
    }
}
