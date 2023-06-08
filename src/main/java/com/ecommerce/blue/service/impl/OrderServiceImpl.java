package com.ecommerce.blue.service.impl;

import com.ecommerce.blue.dao.CustomerRepository;
import com.ecommerce.blue.dao.ProductRepository;
import com.ecommerce.blue.entity.Order;
import com.ecommerce.blue.global.CustomException;
import com.ecommerce.blue.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> createOrder(Map<String, Optional<Object>> orderRequest) throws CustomException {
        String name= (String) orderRequest.get("name").orElseThrow(()->new CustomException(HttpStatus.NOT_ACCEPTABLE.value(), "Name required"));
        String email= (String) orderRequest.get("email").orElseThrow(()->new CustomException(HttpStatus.NOT_ACCEPTABLE.value(), "Email required"));
        var customer= customerRepository.findByEmail(email).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND.value(), "User not found"));
        if(customer.getShoppingKart()==null || customer.getShoppingKart().size()==0)
            throw new CustomException(HttpStatus.EXPECTATION_FAILED.value(), "Shopping cart has empty");
        List<Order> orders=customer.getOrders();
        // Adding product from shopping cart to order
        customer.getShoppingKart().forEach(shoppingKart -> {
            try {
                var order=new Order();
                var productFromDB=productRepository.findById(shoppingKart.getProductId()).orElseThrow(()->new Exception(""));
                order.setProductId(productFromDB.getId());
                order.setQty(shoppingKart.getQty());
                order.setAmount(shoppingKart.getAmount());
                orders.add(order);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        var previousCart=customer.getShoppingKart();
        AtomicReference<Double> totalPrice= new AtomicReference<>((double) 0);
        previousCart.stream().forEach(shoppingKart -> totalPrice.set(totalPrice.get() + shoppingKart.getAmount()));
        // delete after order placed
        customer.setShoppingKart(null);
        var res= customerRepository.save(customer);
        res.setShoppingKart(previousCart);
        Map<String,Object> response=new HashMap<>();
        response.put("body",res);
        response.put("totalPrice",totalPrice.get());
        return ResponseEntity.ok(response);
    }
}
