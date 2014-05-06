package com.innerman.emotracker.web.controller;

import com.innerman.emotracker.core.model.TokenEntity;
import com.innerman.emotracker.core.service.TokenService;
import com.innerman.emotracker.web.data.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by petrpopov on 01.03.14.
 */

@Controller("tokenWebController")
@RequestMapping(value = "/api/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object generateToken() {

        TokenEntity entity = tokenService.generateToken();
        return WebMessage.createOK(entity);
    }
}
