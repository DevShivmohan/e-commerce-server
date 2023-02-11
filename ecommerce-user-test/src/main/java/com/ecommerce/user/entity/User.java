package com.ecommerce.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
//    @Min(value = 3)
//    @Max(40)
    private String name;

    private Date birthDate;
    @Column(unique = true,nullable = false)
    @NotBlank
//    @Min(4)
//    @Max(50)
//    @Pattern(regexp = "[A-Za-z0-9]+")
    private String username;

    @NotBlank
//    @Min(4)
//    @Max(50)
    private String password;

    @NotBlank
//    @Min(4)
//    @Max(30)
    private String role;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "contact",foreignKey = @ForeignKey(name = "FK_contact"))
    private Contact contact;
    @NotBlank
    @Column(unique = true)
    private String createdBy;

    private Date createdOn;
    private Date updatedOn;
}
