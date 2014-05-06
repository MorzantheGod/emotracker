package com.innerman.emotracker.web.data;

import com.innerman.emotracker.core.utils.EmoException;
import com.innerman.emotracker.core.utils.ErrorType;
import com.innerman.emotracker.core.utils.MessageProvider;

import java.io.Serializable;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 22:39
 */

public class WebMessage implements Serializable {

    private MessageState state;
    private String message;
    private Object result;

    private static final String VALIDATION_ERROR = "validation_error";

    public static WebMessage createOK(Object result) {

        WebMessage mes = new WebMessage();

        mes.setState(MessageState.OK);
        mes.setResult(result);

        return mes;
    }

    public static WebMessage createError(String error) {

        WebMessage mes = new WebMessage();

        mes.setState(MessageState.ERROR);
        mes.setMessage(error);

        return mes;
    }

    public static WebMessage createError(ErrorType type) {

        String message = null;
        try {
            message = MessageProvider.getMessage(type);
            return createError(message);
        }
        catch (Exception e) {

        }

        return createError(message);
    }

    public static WebMessage createError(EmoException e) {

        return createError(e.getErrorType());
    }

    public static WebMessage createValidationError() {

        WebMessage mes = new WebMessage();
        mes.setState(MessageState.VALIDATION_ERROR);
        mes.setMessage( MessageProvider.getMessage(VALIDATION_ERROR));

        return mes;
    }

    public MessageState getState() {
        return state;
    }

    public void setState(MessageState state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
