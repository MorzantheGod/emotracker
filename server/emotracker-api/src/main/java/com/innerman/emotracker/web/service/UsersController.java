package com.innerman.emotracker.web.service;


import com.innerman.emotracker.dto.RegistrationDTO;
import com.innerman.emotracker.model.UserEntity;
import com.innerman.emotracker.service.UserService;
import com.innerman.emotracker.utils.EmoException;
import com.innerman.emotracker.web.data.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 22:37
 */

@Controller
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object createNewUser(@Valid RegistrationDTO regDto, BindingResult result) {

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
}
