package com.innerman.emotracker.web.data;

import java.io.Serializable;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 22:39
 */

public class Message implements Serializable {

    private MessageState state;
    private String message;
    private Object result;

    public static Message createOK(Object result) {

        Message mes = new Message();

        mes.setState(MessageState.OK);
        mes.setResult(result);

        return mes;
    }

    public static Message createError(String error) {

        Message mes = new Message();

        mes.setState(MessageState.ERROR);
        mes.setMessage(error);

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
