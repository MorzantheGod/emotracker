package com.innerman.emotracker.web.security;

import com.innerman.emotracker.security.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by petrpopov on 01.03.14.
 */

@Component
public class WebLoginManager {

    @Autowired
    private LoginManager loginManager;

    public Authentication authenticate(String username, String password)
    {
        return this.authenticate(username, password, null);
    }

    public Authentication authenticate(String username, String password, Boolean firstTimeLogin)
    {
        Authentication authentication = loginManager.doAuthenticate(username, password, null, firstTimeLogin);

        return authentication;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response)
    {
        loginManager.logout();

        //rememberMeServices.cancelCookie(request, response);
    }
}
