package org.scem.workflow;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.scem.workflow.constante.JAFlowConstants.*;

@Configuration
public class OpenApiConfig {

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String keycloakClientId;
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String keycloakIssuerUri;

    @Bean
    public OpenAPI customOpenAPI() {

        String description = "Cette API permet de gérer les workflows avec intégration Keycloak";
        String redirect_uri = "http://localhost:8081/api/v3/user/me";
        String swaggerUi = "http://localhost:8081/swagger-ui/index.html";

        String loginUrl = String.format("%s/protocol/openid-connect/auth" +
                "?client_id=%s" +
                "&response_type=code" +
                "&scope=openid" +
                "&redirect_uri=%s",keycloakIssuerUri,keycloakClientId,swaggerUi);
        String loginOut = String.format("%s/protocol/openid-connect/logout" +
                "?post_logout_redirect_uri=%s",keycloakIssuerUri,swaggerUi);

        String desc = String.format("""
                %s 
                <br/><a href="%s" target="_blank">Login</a> 
                &nbsp;&nbsp;<a href="%s" target="_blank">Logout</a> 
                &nbsp;&nbsp;<a href="%s/user/me" target="_blank">whoAmI</a> 
                <br/>Password : admin/admin<br/>""", description,loginUrl,loginOut,redirect_uri);

        return new OpenAPI()
                .info(new Info()
                        .title("JAFlow API")
                        .version("1.0.0")
                        .description(desc)
                        .contact(new Contact()
                                .name("Ton Nom")
                                .email("ton.email@example.com")
                                .url("https://ton-site.fr")
                        )
                );
    }
}

