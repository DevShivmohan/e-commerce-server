package com.ecommerce.blue.controllers;

import com.ecommerce.blue.entity.Customer;
import com.ecommerce.blue.entity.ShoppingKartReqDTO;
import com.ecommerce.blue.global.CustomException;
import com.ecommerce.blue.service.CustomerService;
import com.ecommerce.blue.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ecommerce")
@AllArgsConstructor
public class EcommerceController {
    private CustomerService customerService;
    private OrderService orderService;

    @PostMapping("/add-customer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @PostMapping("/add-cart")
    public ResponseEntity<?> addProductIntoShoppingKart(@RequestBody ShoppingKartReqDTO shoppingKartReqDTO) throws CustomException {
        return customerService.addProductIntoShoppingCart(shoppingKartReqDTO);
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> generateOrder(@RequestBody Map<String, Optional<Object>> request) throws CustomException {
        return orderService.createOrder(request);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProductLists(){
        return customerService.getProducts();
    }
}
