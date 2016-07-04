package com.ilyamur.basic.springmvc.security.core.plugin;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.support.MultipartFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This functionality is unrelated to the spring-security.
 * This hack resolves Jetty + multipart/form-data form + CSRF issue.
 */
public class JettyMultipartFormCsrfFixPlugin extends AbstractSecurityWebApplicationInitializer {

    public class JettyMultipartFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            MultipartConfigElement MULTIPART_CONFIG = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
                request.setAttribute("org.eclipse.jetty.multipartConfig", MULTIPART_CONFIG);
            }
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new JettyMultipartFilter(), new MultipartFilter());
    }
}
