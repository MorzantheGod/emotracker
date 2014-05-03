package com.innerman.emotracker.web.controller;

import com.innerman.emotracker.dto.UserDataDTO;
import com.innerman.emotracker.service.DataService;
import com.innerman.emotracker.utils.EmoException;
import com.innerman.emotracker.web.data.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by petrpopov on 11.03.14.
 */

@Controller
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    public Object saveData(@Valid @RequestBody UserDataDTO data, BindingResult result) {

        if( result.hasErrors() ) {
            return WebMessage.createValidationError();
        }

        try {
            dataService.saveDataForUser(data);
        }
        catch (EmoException e) {
            return WebMessage.createError(e);
        }

        return WebMessage.createOK("OK");
    }
}
