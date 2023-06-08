package com.ecommerce.blue.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ShoppingKart> shoppingKart;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Order> orders;
}
