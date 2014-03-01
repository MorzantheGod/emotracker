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


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public Object createNewUser(@Valid RegistrationDTO dto, BindingResult result) {

        try {
            UserEntity newUser = userService.createNewUser(dto);
            return WebMessage.createOK(newUser);

        } catch (EmoException e) {
            e.printStackTrace();
            return WebMessage.createError(e);
        }
    }
}
