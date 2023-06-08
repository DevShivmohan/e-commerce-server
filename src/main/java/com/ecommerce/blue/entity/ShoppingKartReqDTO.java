package com.ecommerce.blue.entity;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingKartReqDTO {
    private List<ReqProduct> products;
    private String email;
}
