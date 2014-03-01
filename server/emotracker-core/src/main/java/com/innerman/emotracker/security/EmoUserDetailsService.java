package com.innerman.emotracker.security;

import com.innerman.emotracker.model.UserEntity;
import com.innerman.emotracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * User: petrpopov
 * Date: 14.02.13
 * Time: 10:33
 */
@Component
public class EmoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsAssembler userDetailsAssembler;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userService.getUserByUsername(username);

        return convertUser(user);
    }

    private UserDetails convertUser(UserEntity user) throws UsernameNotFoundException {
        if(user == null)
            throw new UsernameNotFoundException("User not found in MongoDB !");

        UserDetails u = userDetailsAssembler.fromUserToUserDetails(user);
        return u;
    }
}
