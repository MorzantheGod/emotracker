package com.innerman.emotracker.model.network;

/**
 * Created by petrpopov on 08.03.14.
 */
public class WebMessage<T> {

    protected MessageState state;
    protected String message;
    protected T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
