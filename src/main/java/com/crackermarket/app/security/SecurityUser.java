package com.crackermarket.app.security;

import com.crackermarket.app.core.BaseEntity;
import com.crackermarket.app.core.Status;
import com.crackermarket.app.user.entities.User;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SecurityUser implements UserDetails {

    private final String userName;
    private final String password;
    private final Set<SimpleGrantedAuthority> authorities;
    private final Boolean isActive;

    public SecurityUser(String userName, String password, Set<SimpleGrantedAuthority> authorities, Boolean isActive) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails detailsFromUser(User user) {
        return new SecurityUser(
                user.getUserName(),
                user.getPassword(),
                user.getRole().getGrantedAuthorities(),
                Status.ACTIVE.equals(user.getStatus()));
    }
}
