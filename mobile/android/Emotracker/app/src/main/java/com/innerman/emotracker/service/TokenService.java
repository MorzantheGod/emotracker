package com.innerman.emotracker.service;

import com.innerman.emotracker.model.TokenMessage;

/**
 * Created by petrpopov on 08.03.14.
 */
public class TokenService extends ApiService<TokenMessage> {

    private String API_URL = "tokens";
    private final String CREATE_USER = "create";

    public TokenService() {
        super(TokenMessage.class);
    }

    public TokenMessage createToken() {

        TokenMessage tokenMessage = this.postForObject(CREATE_USER, null);
        return tokenMessage;
    }

    @Override
    protected String getCurrentApiUrl() {
        return API_URL;
    }
}
