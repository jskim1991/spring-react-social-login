package io.jay.springbootsnslogin.config;

import io.jay.springbootsnslogin.auth.token.JwtSecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class SecretKeyConfiguration {

    @Bean
    public JwtSecretKey secretKey() {
        return new JwtSecretKey(UUID.randomUUID().toString());
    }
}
