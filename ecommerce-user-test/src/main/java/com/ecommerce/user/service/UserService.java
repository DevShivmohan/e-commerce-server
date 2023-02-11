package com.ecommerce.user.service;

import com.ecommerce.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> addUser(User user);
}
