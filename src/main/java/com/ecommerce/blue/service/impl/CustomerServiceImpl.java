package com.ecommerce.blue.service.impl;

import com.ecommerce.blue.dao.CustomerRepository;
import com.ecommerce.blue.dao.ProductRepository;
import com.ecommerce.blue.entity.Customer;
import com.ecommerce.blue.entity.ShoppingKart;
import com.ecommerce.blue.entity.ShoppingKartReqDTO;
import com.ecommerce.blue.global.CustomException;
import com.ecommerce.blue.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    @Override
    public ResponseEntity<?> addCustomer(Customer customer) {
        customer.setId(0);
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @Override
    public ResponseEntity<?> addProductIntoShoppingCart(ShoppingKartReqDTO shoppingKartReqDTO) throws CustomException {
        final Customer cust=customerRepository.findByEmail(shoppingKartReqDTO.getEmail()).orElseThrow(()->
                new CustomException(HttpStatus.NOT_FOUND.value(), "Customer not found"));
        var shoppingKart= cust.getShoppingKart();
        shoppingKartReqDTO.getProducts().stream().forEach(reqProduct -> {
            try{
                // Adding product into existing shopping kart
                var product=productRepository.findById(reqProduct.getProductId()).orElseThrow(()->
                        new CustomException(HttpStatus.NOT_FOUND.value(), "Product not found with id "+reqProduct.getProductId()));
                // if same product existed already in our shopping cart then ignore it
                if(cust.getShoppingKart().stream().
                        filter(product1->product1.getProductId()== reqProduct.getProductId()).collect(Collectors.toList()).size()<1)
                    shoppingKart.add(new ShoppingKart(0,product.getId(), reqProduct.getQty(), reqProduct.getQty() * product.getPrice()));
            }catch (Exception | CustomException e){
                e.printStackTrace();
            }
        });
        AtomicReference<Double> totalPrice= new AtomicReference<>((double) 0);
        cust.getShoppingKart().forEach(shoppingKart1 -> totalPrice.set(totalPrice.get() + shoppingKart1.getAmount()));
        var customer= customerRepository.save(cust);
        Map<String,Object> response=new HashMap<>();
        response.put("body",customer);
        response.put("totalPrice",totalPrice.get());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

}
