package org.scem.workflow.controller;


import org.scem.workflow.dto.LightUserDto;
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

import static org.scem.workflow.constante.JAFlowConstants.*;

@RestController
@RequestMapping(BASE_PATH + "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/me")
    public LightUserDto me(@AuthenticationPrincipal OidcUser principal) {
        return LightUserDto.from(principal);
    }

    //@RolesAllowed("ADMIN")	JSR-250 standard, nécessite @EnableMethodSecurity(jsr250Enabled = true).
    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('BASIC:ADMIN')")	// JSR-250 standard, nécessite @EnableMethodSecurity(jsr250Enabled = true).
    public Map<String, Object> testAdmin(@AuthenticationPrincipal OidcUser principal) {
        return Map.of(
                "name", principal.getFullName(),
                "email", principal.getEmail(),
                "authorities", principal.getAuthorities()
        );
    }
}
