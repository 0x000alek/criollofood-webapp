package com.criollofood.bootapp.config;

import com.criollofood.bootapp.utils.LoginPageFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final LoginPageFilter loginPageFilter;

    public WebSecurityConfig(@Autowired LoginPageFilter loginPageFilter) {
        this.loginPageFilter = loginPageFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginPage = "/login";
        String logoutPage = "/logout";

        http
                .addFilterAfter(loginPageFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(loginPage).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
                    .logoutSuccessUrl(loginPage).and().exceptionHandling();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
