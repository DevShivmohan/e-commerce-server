package com.ecommerce.user.service;

import com.ecommerce.user.model.AuthRequest;
import com.ecommerce.user.model.RefreshRequest;
import com.ecommerce.user.model.VerifyRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> login(AuthRequest authRequest);
    ResponseEntity<?> refreshToken(RefreshRequest refreshRequest);
    ResponseEntity<?> verifyToken(VerifyRequest verifyRequest);
}
