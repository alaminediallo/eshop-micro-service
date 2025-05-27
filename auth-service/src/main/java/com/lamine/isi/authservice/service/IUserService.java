package com.lamine.isi.authservice.service;

import com.lamine.isi.authservice.model.UserCredential;

public interface IUserService {
    boolean validateToken(String token);
    String generateToken(String username);
    UserCredential saveUser(UserCredential credential);
}