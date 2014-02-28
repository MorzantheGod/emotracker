package com.innerman.emotracker.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 23:32
 */

@Component
@Lazy(false)
public class MessageProvider implements MessageSourceAware {

    @Autowired
    private static MessageSource messageSource;

    public static String getMessage(String key) {

        String message = messageSource.getMessage(key, null, new Locale("ru", "RU"));
        return message;
    }

    public static String getMessage(ErrorType error) {

        String key = error.toString();
        return getMessage(key);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        MessageProvider.messageSource = messageSource;
    }
}
