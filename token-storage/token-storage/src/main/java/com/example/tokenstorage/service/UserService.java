package com.example.tokenstorage.service;

import com.example.tokenstorage.entity.User;
import com.example.tokenstorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;

    public UserDetailsService detailsService()
    {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(()->new UsernameNotFoundException("User not Found"));
            }
        };
    }
}
