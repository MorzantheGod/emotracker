package com.innerman.emotracker.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: petrpopov
 * Date: 14.02.2013
 * Time: 11:28
 */

@Component
public class LoginManager {

    @Autowired
    private AuthenticationManager authenticationManager;


    public Authentication authenticate(String username, String password)
    {
        return this.authenticate(username, password, null);
    }

    public Authentication authenticate(String username, String password, Boolean firstTimeLogin)
    {
        Authentication authentication = this.doAuthenticate(username, password, null, firstTimeLogin);

        return authentication;
    }

    public void logout()
    {
        AnonymousAuthenticationToken anonymous = new AnonymousAuthenticationToken("anonymous", "anonymous",
                new ArrayList(Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
        SecurityContextHolder.getContext().setAuthentication(anonymous);
    }

    public Authentication doAuthenticate(String username, String token, Class<?> apiClass, Boolean firstTimeLogin)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, token);

        if( apiClass != null ) {
            authenticationToken.setDetails(new AuthDetails(apiClass, firstTimeLogin));
        }
        else {
            AuthDetails details = new AuthDetails();
            details.setFirstTimeLogin(firstTimeLogin);
            authenticationToken.setDetails(details);
        }

        Authentication authentication = authenticationManager.authenticate( authenticationToken );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

}
