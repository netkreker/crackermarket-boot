package com.crackermarket.app.security;

import com.crackermarket.app.user.entities.User;
import com.crackermarket.app.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(userName);
        if(user == null) {
            throw new UsernameNotFoundException("User with name " + userName + " not found");
        }
        return SecurityUser.detailsFromUser(user);
    }
}
