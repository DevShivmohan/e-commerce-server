package com.ecommerce.user.service.impl;

import com.ecommerce.user.exception.handler.GenericException;
import com.ecommerce.user.util.JwtUtil;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.model.AuthRequest;
import com.ecommerce.user.model.AuthResponse;
import com.ecommerce.user.model.RefreshRequest;
import com.ecommerce.user.model.VerifyRequest;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        var user=userRepository.findByUsername(authRequest.getUsername()).orElseThrow(()->new GenericException("User not found",HttpStatus.NOT_FOUND.value()));
        if(user==null)
            throw new BadCredentialsException("Invalid username or password");
        if(!bCryptPasswordEncoder.matches(authRequest.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Incorrect username or password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(jwtUtil.generateAccessToken(user), jwtUtil.generateRefreshToken(user)));
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshRequest refreshRequest) {
        if(!jwtUtil.isValidRefreshToken(refreshRequest.getRefreshToken()))
            throw new GenericException("Invalid token",HttpStatus.BAD_REQUEST.value());
        var user= userRepository.findByUsername(jwtUtil.extractUsername(refreshRequest.getRefreshToken())).orElseThrow(()-> new GenericException("User not found",HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(jwtUtil.generateAccessToken(user), jwtUtil.generateRefreshToken(user)));
    }

    @Override
    public ResponseEntity<?> verifyToken(VerifyRequest verifyRequest) {
        return null;
    }
}
