package org.scem.workflow.controller;


import org.scem.workflow.dto.LightUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.scem.workflow.constante.JAFlowConstants.*;

@RestController
@RequestMapping(BASE_PATH + "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/me")
    public LightUserDto me(@AuthenticationPrincipal Jwt jwt ) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return LightUserDto.from(jwt);
    }


    //@RolesAllowed("ADMIN")	JSR-250 standard, nécessite @EnableMethodSecurity(jsr250Enabled = true).
    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('BASIC:ADMIN')")	// JSR-250 standard, nécessite @EnableMethodSecurity(jsr250Enabled = true).
    public LightUserDto testAdmin(@AuthenticationPrincipal Jwt jwt) {
        return LightUserDto.from(jwt);
    }
}
