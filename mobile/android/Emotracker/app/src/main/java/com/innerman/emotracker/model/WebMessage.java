package com.innerman.emotracker.model;

/**
 * Created by petrpopov on 08.03.14.
 */
public class WebMessage {

    protected MessageState state;
    protected String message;
    protected Object result;

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
