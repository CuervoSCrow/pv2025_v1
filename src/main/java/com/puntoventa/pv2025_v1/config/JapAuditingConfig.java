package com.puntoventa.pv2025_v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JapAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){
        return () ->    {
            // Si usas Spring Security
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return Optional.of("SISTEMA"); // Valor por defecto
//            }
//            return Optional.of(authentication.getName());

//            Si no usas Spring Security
            return Optional.of("SISTEMA");
        };
    }
}
