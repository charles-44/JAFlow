package org.scem.workflow.dto;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@Data
public class LightUserDto {

    private String username;

    private String email;

    private List<String> authorities;

    public static LightUserDto from(OidcUser oidcUser){
        var user = new LightUserDto();
        if (oidcUser == null) {
            return user;
        }


        user.username = oidcUser.getPreferredUsername();
        user.email = oidcUser.getEmail();
        user.authorities = oidcUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return user;
    }

    public static LightUserDto from(Jwt jwt){
        var user = new LightUserDto();
        if (jwt == null) {
            return user;
        }


        user.username = jwt.getClaim("preferred_username");
        user.email = jwt.getClaim("email");
        //user.authorities = jwt.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return user;
    }
}
