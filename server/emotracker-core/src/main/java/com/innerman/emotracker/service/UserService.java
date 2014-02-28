package com.innerman.emotracker.service;

import com.innerman.emotracker.dto.RegistrationDTO;
import com.innerman.emotracker.model.UserEntity;
import com.innerman.emotracker.utils.EmoException;
import com.innerman.emotracker.utils.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 21:26
 */

@Component
public class UserService extends GenericService<UserEntity> {

    public UserService() {
        super(UserEntity.class);
        logger = Logger.getLogger(UserService.class);
    }


    public UserEntity createNewUser(RegistrationDTO dto) throws EmoException {

        UserEntity byEmail = findByEmail(dto.getEmail());
        if( byEmail != null ) {
            throw new EmoException(ErrorType.user_already_exists);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(dto.getFullName());
        userEntity.setEmail(dto.getEmail());
        userEntity.setUserName(dto.getUserName());

        op.save(userEntity);
        UserEntity saved = findByEmail(dto.getEmail());
        return saved;
    }

    public UserEntity findByEmail(String email) {
        Criteria criteria = Criteria.where("email").is(email);
        Query query = new Query(criteria);

        return op.findOne(query, UserEntity.class);
    }
}
