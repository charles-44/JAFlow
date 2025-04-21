package org.scem.workflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("${server.url}")
    private String serverUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String keycloakClientId;
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String keycloakIssuerUri;

    @Bean
    public OpenAPI customOpenAPI() {

        String description = "Cette API permet de gérer les workflows avec intégration Keycloak";
        String redirect_uri = serverUrl + "/api/v3/user/me";
        String swaggerUi = serverUrl + "/swagger-ui/index.html";

        String loginUrl = String.format("%s/protocol/openid-connect/auth" +
                "?client_id=%s" +
                "&response_type=code" +
                "&scope=openid" +
                "&redirect_uri=%s",keycloakIssuerUri,keycloakClientId,redirect_uri);
        String loginOut = String.format("%s/protocol/openid-connect/logout" +
                "?post_logout_redirect_uri=%s",keycloakIssuerUri,swaggerUi);

        String desc = String.format("""
                %s 
                <br/><a href="%s" target="_blank">Login</a> 
                &nbsp;&nbsp;<a href="%s" target="_blank">Logout</a> 
                &nbsp;&nbsp;<a href="%s" target="_blank">whoAmI</a> 
                <br/>Password : admin/admin<br/>""", description,loginUrl,loginOut,redirect_uri);

        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription("Base path");

        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("JAFlow API")
                        .version("1.0.0")
                        .description(desc)
                        .contact(new Contact()
                                .name("Ton Nom")
                                .email("ton.email@example.com")
                                .url("https://ton-site.fr")
                        )
                ).components(new Components().addSecuritySchemes("keycloak", new SecurityScheme()
                        .type(SecurityScheme.Type.OAUTH2)
                        .description("OAuth2 avec Keycloak")
                        .flows(new OAuthFlows().authorizationCode(
                                new OAuthFlow()
                                        .authorizationUrl("http://localhost:8000/keycloak/realms/baeldung-keycloak/protocol/openid-connect/auth")
                                        .tokenUrl("http://localhost:8000/keycloak/realms/baeldung-keycloak/protocol/openid-connect/token")
                                        .scopes(new Scopes().addString("openid", "OpenID Connect scope"))
                        ))))
                .addSecurityItem(new SecurityRequirement().addList("keycloak"));
    }
}

