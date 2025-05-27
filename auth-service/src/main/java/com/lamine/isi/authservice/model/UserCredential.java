package com.lamine.isi.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredential {
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
