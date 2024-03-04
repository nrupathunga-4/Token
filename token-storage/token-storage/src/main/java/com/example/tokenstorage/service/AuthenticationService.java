package com.example.tokenstorage.service;

import com.example.tokenstorage.entity.User;
import com.example.tokenstorage.payload.UserRequest;
import com.example.tokenstorage.payload.UserResponse;
import com.example.tokenstorage.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    public UserResponse saveUser(UserRequest request)
    {
        if (request.getEmail() == null)
        {
            throw new RuntimeException("parameter email is not found in request..!");
        } else if (request.getPassword() == null) {
            throw new RuntimeException("parameter password is not found in request..!");
        }
        User savedUser = null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = request.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        User user = modelMapper.map(request,User.class);
        user.setPassword(encodedPassword);
        if(request.getId() != null)
        {
            User oldUser = userRepository.findById(request.getId());
            if(oldUser != null)
            {
                oldUser.setId(user.getId());
                oldUser.setEmail(user.getEmail());
                oldUser.setPassword(user.getPassword());
                oldUser.setRole(user.getRole());

                savedUser = userRepository.save(oldUser);
            }else {
                throw new RuntimeException("Can't find record with identifier: " + request.getId());
            }

        }else {
            user.setCreatedBy()
        }
    }

}
