package com.ecommerce.blue.service;

import com.ecommerce.blue.entity.Customer;
import com.ecommerce.blue.entity.ShoppingKartReqDTO;
import com.ecommerce.blue.global.CustomException;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> addCustomer(Customer customer);
    ResponseEntity<?> addProductIntoShoppingCart(final ShoppingKartReqDTO shoppingKartReqDTO) throws CustomException;
    ResponseEntity<?> getProducts();
}
