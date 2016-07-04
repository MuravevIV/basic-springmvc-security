package com.ilyamur.basic.springmvc.security.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import({
        ThymeleafConfiguration.class
})
@EnableWebMvc
@ComponentScan
public class WebApplicationConfiguration {
}
