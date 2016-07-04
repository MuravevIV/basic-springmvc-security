package com.ilyamur.basic.springmvc.security.web;

import com.ilyamur.basic.springmvc.security.core.WebSecurityConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Remove WebSecurityConfiguration from the list here to disable all security features.
        return new Class<?>[]{WebSecurityConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebApplicationConfiguration.class};
    }
}
