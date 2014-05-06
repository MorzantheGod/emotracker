package com.innerman.emotracker.web.controller;


import com.innerman.emotracker.core.dto.LoginDTO;
import com.innerman.emotracker.core.dto.RegistrationDTO;
import com.innerman.emotracker.core.model.UserEntity;
import com.innerman.emotracker.core.service.UserService;
import com.innerman.emotracker.core.utils.EmoException;
import com.innerman.emotracker.core.utils.ErrorType;
import com.innerman.emotracker.web.data.WebMessage;
import com.innerman.emotracker.web.security.WebLoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 22:37
 */

@Controller("usersWebController")
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private WebLoginManager loginManager;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object createNewUser(@Valid @RequestBody RegistrationDTO regDto, BindingResult result) {

        if( result.hasErrors() ) {
            return WebMessage.createValidationError();
        }

        try {
            UserEntity newUser = userService.createNewUser(regDto);
            return WebMessage.createOK(newUser);

        } catch (EmoException e) {
            e.printStackTrace();
            return WebMessage.createError(e);
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object loginUser(@Valid @RequestBody LoginDTO dto, BindingResult result) {

        if( result.hasErrors() ) {
            return WebMessage.createValidationError();
        }

        UserEntity entity = userService.getUserByUsernameOrEmail(dto.getUserName());
        if( entity == null ) {
            return WebMessage.createError(ErrorType.no_such_user);
        }

        try {
            loginManager.authenticate(dto.getUserName(), dto.getPassword());
            return WebMessage.createOK(entity);
        }
        catch (BadCredentialsException e) {
            return WebMessage.createError("ERROR");
        }
    }
}
