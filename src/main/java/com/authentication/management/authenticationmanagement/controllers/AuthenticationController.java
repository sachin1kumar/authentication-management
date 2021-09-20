package com.authentication.management.authenticationmanagement.controllers;

import com.authentication.management.authenticationmanagement.models.AuthenticationRequest;
import com.authentication.management.authenticationmanagement.models.AuthenticationResponse;
import com.authentication.management.authenticationmanagement.models.AuthorizationResponse;
import com.authentication.management.authenticationmanagement.services.CustomUserDetailService;
import com.authentication.management.authenticationmanagement.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    private UserDetails userDetails;

    @GetMapping("/authorization")
    public AuthorizationResponse authorizeUser(@RequestHeader("Authorization") String bearerToken) {
        final AuthorizationResponse authorizationResponse =
                customUserDetailService.getAuthorizedUserDetails(userDetails.getUsername());
        final AuthorizationResponse modifiedResponse = new AuthorizationResponse();
        modifiedResponse.setRoleType(authorizationResponse.getRoleType());
        return authorizationResponse;
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest
                    .getEmailId(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new Exception("Invalid username or password", badCredentialsException);
        }
        userDetails = customUserDetailService.loadUserByUsername(authenticationRequest.getEmailId());
        final String jwtToken = JwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
