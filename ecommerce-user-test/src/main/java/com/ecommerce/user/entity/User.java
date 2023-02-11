package com.ecommerce.user.entity;

import javax.persistence.*;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Date birthDate;
    @Column(unique = true,nullable = false)
    private String username;
    private String password;
    private String role;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "contact",foreignKey = @ForeignKey(name = "FK_contact"))
    private Contact contact;
    private String createdBy;
    private Date createdOn;
    private Date updatedOn;
}
