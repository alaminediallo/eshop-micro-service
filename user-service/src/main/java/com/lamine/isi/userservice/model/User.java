package com.lamine.isi.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // renvoyer une bad request quand l'user
    @Column(unique = true)
    private String username;
    private String email;
    private String password;

    @Column(name = "product_id")
    private Long productId;

}
