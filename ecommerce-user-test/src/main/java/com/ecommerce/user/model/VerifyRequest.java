package com.ecommerce.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyRequest {
    private String verifyToken;
    private String otp;
}
