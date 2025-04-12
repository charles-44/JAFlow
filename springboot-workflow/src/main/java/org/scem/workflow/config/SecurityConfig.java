package org.scem.workflow.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Value( "${jaflow.keycloak.url.protocol}" )
    private String keycloakUrlProtocol;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       logger.info("securityFilterChain test keycloakUrlProtocol : {}",keycloakUrlProtocol);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(withDefaults()) // Redirige vers page login OAuth2
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        return http.build();
    }
}
