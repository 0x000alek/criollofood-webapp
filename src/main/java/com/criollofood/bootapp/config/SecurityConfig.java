package com.criollofood.bootapp.config;

import com.criollofood.bootapp.utils.AuthenticationFacade;
import com.criollofood.bootapp.utils.Pbkdf2Sha256PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public AuthenticationFacade authenticationFacade() {
        return new AuthenticationFacade();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Pbkdf2Sha256PasswordEncoder pbkdf2Sha256PasswordEncoder() {
        return new Pbkdf2Sha256PasswordEncoder();
    }
}
