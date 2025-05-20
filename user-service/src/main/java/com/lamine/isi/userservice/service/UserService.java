package com.lamine.isi.userservice.service;

import com.lamine.isi.userservice.exception.NotFoundException;
import com.lamine.isi.userservice.model.UserCredential;
import com.lamine.isi.userservice.repository.IUserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserCredentialRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserCredential> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserCredential getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Override
    public UserCredential getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
    }

    @Override
    public UserCredential save(UserCredential user) {
        return userRepository.save(user);
    }

    @Override
    public UserCredential update(Long id, UserCredential user) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public UserCredential partialUpdate(Long id, Map<String, Object> updates) {
        UserCredential existingUser = getById(id);

        if (updates.containsKey("username")) {
            existingUser.setUsername((String) updates.get("username"));
        }
        if (updates.containsKey("email")) {
            existingUser.setEmail((String) updates.get("email"));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public UserCredential saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        return userRepository.save(credential);
    }

}