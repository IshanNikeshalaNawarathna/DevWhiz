package com.DevWhiz.blog.controller;

import com.DevWhiz.blog.domain.dto.AuthResponse;
import com.DevWhiz.blog.domain.dto.LoginRequest;
import com.DevWhiz.blog.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String generateToken = authenticationService.generateToken(userDetails);
        AuthResponse authToken = AuthResponse.builder()
                .token(generateToken)
                .expiresIn(84000)
                .build();
        return ResponseEntity.ok(authToken);
    }


}
