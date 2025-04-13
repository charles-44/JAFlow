package org.scem.workflow.dto;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

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
}
