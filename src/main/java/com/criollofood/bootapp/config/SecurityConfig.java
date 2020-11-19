package com.criollofood.bootapp.config;

import com.criollofood.bootapp.utils.AESEncrypter;
import com.criollofood.bootapp.utils.AuthenticationFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFacade authenticationFacade() {
        return new AuthenticationFacade();
    }

    @Bean
    public AESEncrypter aesEncrypter() {
        return new AESEncrypter();
    }
}
