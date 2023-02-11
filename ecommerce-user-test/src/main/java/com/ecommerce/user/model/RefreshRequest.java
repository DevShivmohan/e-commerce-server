package com.ecommerce.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshRequest {
    private String refreshToken;
}
