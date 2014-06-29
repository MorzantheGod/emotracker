package com.innerman.emotracker.core.service;

import com.innerman.emotracker.core.dto.RegistrationDTO;
import com.innerman.emotracker.core.model.TokenEntity;
import com.innerman.emotracker.core.model.UserEntity;
import com.innerman.emotracker.core.model.UserRole;
import com.innerman.emotracker.core.security.EmoPasswordEncoder;
import com.innerman.emotracker.core.utils.EmoException;
import com.innerman.emotracker.core.utils.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 21:26
 */

@Component
public class UserEntityService extends GenericEntityService<UserEntity> {

    @Autowired
    private EmoPasswordEncoder encoder;

    @Autowired
    private TokenEntityService tokenService;

    @Value("#{properties.admin_username}")
    private String adminUsername;

    private static final String USERNAME_FIELD = "userName";
    private static final String EMAIL_FIELD = "email";
    private static final String TOKEN_KEY_FIELD = "tokens.key";

    public UserEntityService() {
        super(UserEntity.class);
        logger = Logger.getLogger(UserEntityService.class);
    }


    public UserEntity createNewUser(RegistrationDTO dto) throws EmoException {

        UserEntity byEmail = getUserByEmail(dto.getEmail());
        if( byEmail != null ) {
            throw new EmoException(ErrorType.email_already_exists);
        }

        UserEntity byUsername = getUserByUsername(dto.getUserName());
        if( byUsername != null ) {
            throw new EmoException(ErrorType.username_already_exists);
        }

        UserEntity userToSave = new UserEntity();
        userToSave.setFullName(dto.getFullName());
        userToSave.setEmail(dto.getEmail());
        userToSave.setUserName(dto.getUserName());

        //setting default roles
        List<UserRole> roles = userToSave.getRoles();
        if( roles == null ) {
            roles = new ArrayList<UserRole>();
            roles.add(UserRole.getRoleUser());

            userToSave.setRoles(roles);
        }


        //checking token
        TokenEntity tokenByKey = tokenService.getTokenByKey(dto.getKey());
        if( tokenByKey == null ) {
            throw new EmoException(ErrorType.token_invalid);
        }
        TokenEntity tokenByValue = tokenService.getTokenByValue(dto.getToken());
        if(tokenByValue == null) {
            throw new EmoException(ErrorType.token_invalid);
        }

        UserEntity userByToken = getUserByToken(dto.getKey());
        if( userByToken != null ) {
            throw new EmoException(ErrorType.token_invalid);
        }

        List<TokenEntity> tokens = userToSave.getTokens();
        if( tokens == null ) {
            tokens = new ArrayList<TokenEntity>();
            userToSave.setTokens(tokens);
        }
        tokens.add( new TokenEntity(dto.getKey(), dto.getToken()) );


        UserEntity saved = updatePasswordForUser(userToSave, dto.getPassword());
        return saved;
    }

    public UserEntity getAdminUserEntity() {

        UserEntity entity = new UserEntity();
        entity.setId(adminUsername);
        entity.setEmail(adminUsername);

        return entity;
    }

//    @Cacheable(value = "users", key = "#id")
    public UserEntity getUserById(String id) {
        return this.findById(id);
    }

    public UserEntity getUserByUsernameOrEmail(String username) {

        UserEntity entity = this.getUserByEmail(username);
        if( entity == null ) {
            entity = this.getUserByUsername(username);
        }
        return entity;
    }

    public UserEntity getUserByUsername(String username) {
        Criteria criteria = Criteria.where(USERNAME_FIELD).is(username);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    public UserEntity getUserByEmail(String email) {
        Criteria criteria = Criteria.where(EMAIL_FIELD).is(email);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    protected UserEntity getUserByToken(String key) {
        Criteria criteria = Criteria.where(TOKEN_KEY_FIELD).is(key);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }

    protected UserEntity updatePasswordForUser(UserEntity userToSave, String password) {

        //generate random salt
        UUID randomUUID = UUID.randomUUID();
        String newSalt = randomUUID.toString();


        String encodePassword = encoder.encodePassword(password, newSalt);
        userToSave.setPasswordHash(encodePassword);
        userToSave.setSalt(newSalt);

        op.save(userToSave);
        return getUserByEmail(userToSave.getEmail());
    }

}
