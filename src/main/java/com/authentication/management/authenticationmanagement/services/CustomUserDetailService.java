package com.authentication.management.authenticationmanagement.services;

import com.authentication.management.authenticationmanagement.models.AuthorizationResponse;
import com.authentication.management.authenticationmanagement.models.Users;
import com.authentication.management.authenticationmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {
    //Returning hardcoded user due to no db support in this project for now.

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        final Users user = userRepository.findByEmailId(emailId);
        return new User(user.getEmailId(), user.getPassword(), new ArrayList<>());
    }

    public AuthorizationResponse getAuthorizedUserDetails(String emailId) {
        final Users user = userRepository.findByEmailId(emailId);
        final AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setRoleType(user.getRoleType());
        return authorizationResponse;
    }
}
