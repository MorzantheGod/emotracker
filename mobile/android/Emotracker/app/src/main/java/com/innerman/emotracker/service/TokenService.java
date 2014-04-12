package com.innerman.emotracker.service;

import com.innerman.emotracker.model.network.TokenDTO;
import com.innerman.emotracker.model.network.WebMessage;

/**
 * Created by petrpopov on 08.03.14.
 */
public class TokenService extends ApiService<TokenDTO> {

    private String API_URL = "tokens";
    private final String CREATE_USER = "create";

    public TokenService() {
        super(TokenDTO.class);
    }

    public WebMessage<TokenDTO> createToken() {

        WebMessage<TokenDTO> webMessage = this.postForObject(CREATE_USER, null);
        return webMessage;
    }

    @Override
    protected String getCurrentApiUrl() {
        return API_URL;
    }
}
