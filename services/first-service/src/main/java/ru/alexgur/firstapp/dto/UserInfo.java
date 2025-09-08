package ru.alexgur.firstapp.dto;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alexgur.firstapp.model.User;

import java.util.Arrays;
import java.util.Collection;

public class UserInfo implements UserDetails {
    private final User user;

    public UserInfo(User user) {
        this.user = user;
    }

    public boolean hasRole(String role) {
        return Arrays.asList(user.getRole().split(", "))
                .contains("ROLE_" + role.toUpperCase());
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRole().split(", "))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + user.getId() + ", " +
                "username=" + user.getUsername() + ", " +
                "role=" + user.getRole() +
                '}';
    }
}