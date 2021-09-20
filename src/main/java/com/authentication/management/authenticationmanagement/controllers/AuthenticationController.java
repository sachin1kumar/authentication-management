package com.authentication.management.authenticationmanagement.controllers;

import com.authentication.management.authenticationmanagement.models.AuthenticationRequest;
import com.authentication.management.authenticationmanagement.models.AuthenticationResponse;
import com.authentication.management.authenticationmanagement.services.CustomUserDetailService;
import com.authentication.management.authenticationmanagement.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/user")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest
                    .getEmailId(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new Exception("Invalid username or password", badCredentialsException);
        }
        final UserDetails userDetails = customUserDetailService.loadUserByUsername(authenticationRequest.getEmailId());
        final String jwtToken = JwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
