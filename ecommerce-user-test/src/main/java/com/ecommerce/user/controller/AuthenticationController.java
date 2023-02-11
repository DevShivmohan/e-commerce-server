package com.ecommerce.user.controller;

import com.ecommerce.user.constants.ApiConstants;
import com.ecommerce.user.model.RefreshRequest;
import com.ecommerce.user.service.AuthenticationService;
import com.ecommerce.user.model.AuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.AUTHENTICATION)
@Slf4j
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping(ApiConstants.LOGIN)
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest){
        return authenticationService.login(authRequest);
    }

    @PostMapping(ApiConstants.REFRESH)
    public ResponseEntity<?> generateRefreshToken(@RequestBody RefreshRequest refreshRequest){
        log.info(refreshRequest.toString());
        return authenticationService.refreshToken(refreshRequest);
    }
}
