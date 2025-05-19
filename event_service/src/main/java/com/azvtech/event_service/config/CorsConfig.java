package com.azvtech.event_service.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization", "Content-Disposition") // Importante para downloads
                        .allowCredentials(true);

                // Para Actuator (se necessário)
                registry.addMapping("/actuator/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET");
            }
        };
    }
}
