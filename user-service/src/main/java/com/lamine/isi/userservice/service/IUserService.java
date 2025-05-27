package com.lamine.isi.userservice.service;

import com.lamine.isi.userservice.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    List<User> getAll();

    User getById(Long id);

    User getByUsername(String username);

    User save(User user);

    User update(Long id, User user);

    User partialUpdate(Long id, Map<String, Object> updates);

    void delete(Long id);
}