package com.criollofood.bootapp.config;

import com.criollofood.bootapp.service.CriolloFoodUserDetailsService;
import com.criollofood.bootapp.utils.LoginPageFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CriolloFoodUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LoginPageFilter loginPageFilter;

    public WebSecurityConfig(@Autowired CriolloFoodUserDetailsService userDetailsService,
                             @Autowired BCryptPasswordEncoder bCryptPasswordEncoder,
                             @Autowired LoginPageFilter loginPageFilter) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginPageFilter = loginPageFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginPage = "/login";
        String logoutPage = "/logout";
        String homePage = "/";

        http
                .addFilterAfter(loginPageFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage(loginPage)
                .failureUrl("/login?error=true")
                .defaultSuccessUrl(homePage)
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
                .logoutSuccessUrl(homePage).and().exceptionHandling();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
