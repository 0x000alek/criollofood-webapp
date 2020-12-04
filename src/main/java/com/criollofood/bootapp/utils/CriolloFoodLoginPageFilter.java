package com.criollofood.bootapp.utils;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

@Component
public class CriolloFoodLoginPageFilter extends GenericFilterBean {
    private final AuthenticationFacade authenticationFacade;

    public CriolloFoodLoginPageFilter(@Autowired AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (authenticationFacade.isAuthenticated() && new HashSet<>(Collections.singletonList("/login")).contains(request.getRequestURI())) {

            String encodedRedirectURL = response.encodeRedirectURL(
                    request.getContextPath() + "/");

            response.setStatus(HttpStatus.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", encodedRedirectURL);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
