package ru.alexgur.firstapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alexgur.firstapp.dto.UserInfo;
import ru.alexgur.firstapp.model.User;
import ru.alexgur.firstapp.reposiitory.UserRepository;

import java.util.Optional;

@Slf4j
@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);

        return user.map(UserInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}