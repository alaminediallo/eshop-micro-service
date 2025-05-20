package com.lamine.isi.userservice.service;

import com.lamine.isi.userservice.model.UserCredential;

import java.util.List;
import java.util.Map;

public interface IUserService {
    List<UserCredential> getAll();

    UserCredential getById(Long id);

    UserCredential getByUsername(String username);

    UserCredential save(UserCredential user);

    UserCredential update(Long id, UserCredential user);

    UserCredential partialUpdate(Long id, Map<String, Object> updates);

    void delete(Long id);

    boolean validateToken(String token);
}