package com.criollofood.bootapp.config;

import com.criollofood.bootapp.service.CriolloFoodUserDetailsService;
import com.criollofood.bootapp.utils.CriolloFoodAuthenticationSuccessHandler;
import com.criollofood.bootapp.utils.CriolloFoodLoginPageFilter;
import com.criollofood.bootapp.utils.Pbkdf2Sha256PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CriolloFoodAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CriolloFoodUserDetailsService userDetailsService;
    private final CriolloFoodLoginPageFilter loginPageFilter;
    private final Pbkdf2Sha256PasswordEncoder pbkdf2Sha256PasswordEncoder;

    public WebSecurityConfig(@Autowired CriolloFoodAuthenticationSuccessHandler authenticationSuccessHandler,
                             @Autowired CriolloFoodUserDetailsService userDetailsService,
                             @Autowired CriolloFoodLoginPageFilter loginPageFilter,
                             @Autowired Pbkdf2Sha256PasswordEncoder pbkdf2Sha256PasswordEncoder) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.loginPageFilter = loginPageFilter;
        this.pbkdf2Sha256PasswordEncoder = pbkdf2Sha256PasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(pbkdf2Sha256PasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginPage = "/login";
        String logoutPage = "/logout";

        http
                .addFilterAfter(loginPageFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/reporteria*").hasAuthority("ADMINISTRADOR")
                    .antMatchers("/recepcion*").hasAuthority("RECEPCION")
                    .antMatchers("/cocina*").hasAuthority("COCINA")
                    .antMatchers("/login*").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage(loginPage)
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(authenticationSuccessHandler)
                        .failureUrl("/login?error=true")
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
                        .logoutSuccessUrl(loginPage)
                        .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403")
                .and()
                    .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
