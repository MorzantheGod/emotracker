package com.innerman.emotracker.web.service;


import com.innerman.emotracker.config.EmoException;
import com.innerman.emotracker.config.MessageProvider;
import com.innerman.emotracker.dto.RegistrationDTO;
import com.innerman.emotracker.model.UserEntity;
import com.innerman.emotracker.service.UserService;
import com.innerman.emotracker.web.data.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private MessageProvider messageProvider;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object createNewUser(@Valid @RequestBody RegistrationDTO dto) {

        try {
            UserEntity newUser = userService.createNewUser(dto);
            return Message.createOK(newUser);

        } catch (EmoException e) {
            e.printStackTrace();
            return Message.createError(messageProvider.getMessage(e.getErrorType()));
        }
    }
}
