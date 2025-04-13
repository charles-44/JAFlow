package org.scem.workflow.config;

import org.scem.workflow.config.service.CustomOidcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.scem.workflow.constante.JAFlowConstants.BASE_PATH;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {



    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                BASE_PATH +  "/user/me",
                                "/swagger-ui/**",
                                "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(new CustomOidcUserService()))
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtConverter = new JwtGrantedAuthoritiesConverter();
        jwtConverter.setAuthorityPrefix("ROLE_"); // Important pour que hasRole('ADMIN') fonctionne
        jwtConverter.setAuthoritiesClaimName("realm_access.roles");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtConverter);
        return converter;
    }


}
