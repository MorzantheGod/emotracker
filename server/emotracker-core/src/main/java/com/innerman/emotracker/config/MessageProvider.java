package com.innerman.emotracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 23:32
 */

@Component
public class MessageProvider {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {

        String message = messageSource.getMessage(key, null, Locale.US);
        return message;
    }

    public String getMessage(ErrorType error) {

        String key = error.toString();
        return getMessage(key);
    }
}
