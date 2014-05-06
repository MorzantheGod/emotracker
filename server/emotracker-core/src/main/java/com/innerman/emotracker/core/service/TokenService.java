package com.innerman.emotracker.core.service;

import com.innerman.emotracker.core.model.TokenEntity;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by petrpopov on 01.03.14.
 */

@Component
public class TokenService extends GenericService<TokenEntity> {

    public TokenService() {
        super(TokenEntity.class);
        logger = Logger.getLogger(TokenService.class);
    }

    public TokenEntity generateToken() {

        String key;

        //paranoia style
        while(true) {
            key = generateUUID();
            TokenEntity tokenByKey = this.getTokenByKey(key);
            if( tokenByKey == null ) {
                break;
            }
        }

        //paranoia style
        String token;
        while(true) {
            token = generateUUID();
            TokenEntity tokenByToken = this.getTokenByValue(token);
            if( tokenByToken == null ) {
                break;
            }
        }

        TokenEntity entity = new TokenEntity();
        entity.setKey(key);
        entity.setToken(token);
        entity.setEternal(Boolean.TRUE);
        entity.setValid(Boolean.TRUE);

        op.save(entity);
        TokenEntity byKey = getTokenByKey(key);
        return byKey;
    }

    public TokenEntity getTokenByKey(String key) {

        Criteria criteria = Criteria.where("key").is(key);
        Query query = new Query(criteria);

        return op.findOne(query, TokenEntity.class);
    }

    public TokenEntity getTokenByValue(String token) {

        Criteria criteria = Criteria.where("token").is(token);
        Query query = new Query(criteria);

        return op.findOne(query, TokenEntity.class);
    }

    private String generateUUID() {
        UUID uuid = UUID.randomUUID();

        String suid = uuid.toString();
        suid = suid.replaceAll("-", "");

        return suid;
    }
}
