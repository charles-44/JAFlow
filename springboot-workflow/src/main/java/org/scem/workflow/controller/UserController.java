package org.scem.workflow.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public Map<String, Object> me(@AuthenticationPrincipal OidcUser principal) {
        return Map.of(
                "name", principal.getFullName(),
                "email", principal.getEmail(),
                "authorities", principal.getAuthorities()
        );
    }

    //@RolesAllowed("ADMIN")	JSR-250 standard, nécessite @EnableMethodSecurity(jsr250Enabled = true).
    @GetMapping("/test/admin")
    @RolesAllowed("ADMIN")	// JSR-250 standard, nécessite @EnableMethodSecurity(jsr250Enabled = true).
    public Map<String, Object> testAdmin(@AuthenticationPrincipal OidcUser principal) {
        return Map.of(
                "name", principal.getFullName(),
                "email", principal.getEmail(),
                "authorities", principal.getAuthorities()
        );
    }
}
