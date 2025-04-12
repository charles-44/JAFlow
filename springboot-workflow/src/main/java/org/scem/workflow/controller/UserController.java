package org.scem.workflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //@GetMapping("/me")
    //

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN)')")
    public Map<String, Object> me(Authentication authentication) {
        logger.info("======= SecurityContext Roles =======");
        authentication.getAuthorities().forEach(
                authority -> logger.info("ROLE: {}", authority.getAuthority())
        );

        Object principal = authentication.getPrincipal();
        String name = "unknown";
        String email = "unknown";

        if (principal instanceof OidcUser oidcUser) {
            name = oidcUser.getFullName();
            email = oidcUser.getEmail();
        }

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Map.of(
                "name", name,
                "email", email,
                "roles", roles
        );
    }

    //@RolesAllowed("ADMIN")	JSR-250 standard, nécessite @EnableMethodSecurity(jsr250Enabled = true).
    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('ADMIN')")	// JSR-250 standard, nécessite @EnableMethodSecurity(jsr250Enabled = true).
    public Map<String, Object> testAdmin(@AuthenticationPrincipal OidcUser principal) {
        return Map.of(
                "name", principal.getFullName(),
                "email", principal.getEmail(),
                "authorities", principal.getAuthorities()
        );
    }
}
