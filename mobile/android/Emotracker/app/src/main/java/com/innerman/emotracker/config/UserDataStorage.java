package com.innerman.emotracker.config;

import android.content.Context;

import com.innerman.emotracker.model.TokenDTO;
import com.innerman.emotracker.model.UserDTO;
import com.innerman.emotracker.model.UserRoleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 09.03.14.
 */
public class UserDataStorage extends SimpleDataStorage {

    private static final String USER_ID = "userId";
    private static final String FULLNAME = "fullName";
    private static final String USERNAME = "userName";
    private static final String EMAIL = "email";
    private static final String ROLE_NAME = "roleName";
    private static final String TOKEN_ID = "tokenId";
    private static final String TOKEN_KEY = "tokeKey";
    private static final String TOKEN = "token";

    public UserDataStorage(Context context, String configName) {
        super(context, configName);
    }

    public void saveUser(UserDTO user) {

        setString(USER_ID, user.getId());
        setString(FULLNAME, user.getFullName());
        setString(USERNAME, user.getUserName());
        setString(EMAIL, user.getEmail());

        if( user.getRoles() != null ) {
            if(!user.getRoles().isEmpty()) {
                UserRoleDTO role = user.getRoles().get(0);
                setString(ROLE_NAME, role.getName());
            }
        }

        if( user.getTokens() != null ) {
            if( !user.getTokens().isEmpty() ) {
                TokenDTO token = user.getTokens().get(0);

                setString(TOKEN_ID, token.getId());
                setString(TOKEN_KEY, token.getKey());
                setString(TOKEN, token.getToken());
            }
        }
    }

    public boolean isUserValid() {
        UserDTO user = getUser();
        if( user == null ) {
            return false;
        }

        if( user.getFullName() == null || user.getEmail() == null || user.getUserName() == null ) {
            return false;
        }

        List<UserRoleDTO> roles = user.getRoles();
        if(roles == null || roles.isEmpty()) {
            return false;
        }

        UserRoleDTO roleDTO = roles.get(0);
        if( roleDTO == null ) {
            return false;
        }

        if( roleDTO.getName() == null ) {
            return false;
        }

        List<TokenDTO> tokens = user.getTokens();
        if( tokens == null || tokens.isEmpty() ) {
            return false;
        }

        TokenDTO tokenDTO = tokens.get(0);
        if( tokenDTO == null ) {
            return false;
        }

        String key = tokenDTO.getKey();
        if( key == null ) {
            return false;
        }

        if( tokenDTO.getToken() == null ) {
            return false;
        }

        return true;
    }

    public UserDTO getUser() {

        UserDTO dto = new UserDTO();

        dto.setId( getString(USER_ID));
        dto.setFullName( getString(FULLNAME));
        dto.setUserName( getString(USERNAME));
        dto.setEmail( getString(EMAIL));

        UserRoleDTO role = new UserRoleDTO();
        role.setName( getString(ROLE_NAME));

        List<UserRoleDTO> roles = new ArrayList<UserRoleDTO>();
        roles.add(role);

        dto.setRoles(roles);

        TokenDTO token = new TokenDTO();
        token.setId( getString(TOKEN_ID));
        token.setKey( getString(TOKEN_KEY));
        token.setToken( getString(TOKEN));

        List<TokenDTO> tokens = new ArrayList<TokenDTO>();
        tokens.add(token);

        dto.setTokens(tokens);

        return dto;
    }
}
