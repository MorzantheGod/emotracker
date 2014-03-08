package com.innerman.emotracker.service;

import com.innerman.emotracker.model.RegistrationDTO;
import com.innerman.emotracker.model.WebMessage;

/**
 * Created by petrpopov on 08.03.14.
 */
public class UserService extends ApiService<WebMessage> {

    private String API_URL = "users";
    private final String CREATE_USER = "create";
    private final String TEST_USER = "test";

    public UserService() {
        super(WebMessage.class);
    }

    public WebMessage testUser() {

        WebMessage test = this.getForObject(TEST_USER);
        return test;
    }

    public WebMessage signUpUser(RegistrationDTO dto) {

        WebMessage createUser = this.postForObject(CREATE_USER, dto);

        return createUser;
    }

    @Override
    protected String getCurrentApiUrl() {
        return API_URL;
    }
}
