package com.ecommerce.blue.service;

import com.ecommerce.blue.global.CustomException;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface OrderService {
    ResponseEntity<?> createOrder(final Map<String, Optional<Object>> orderRequest) throws CustomException;
}
