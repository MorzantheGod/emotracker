package com.innerman.emotracker.model;

/**
 * Created by petrpopov on 08.03.14.
 */
public class TokenMessage  {

    protected MessageState state;
    protected String message;
    private TokenDTO result;

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

    public TokenDTO getResult() {
        return result;
    }

    public void setResult(TokenDTO result) {
        this.result = result;
    }
}
