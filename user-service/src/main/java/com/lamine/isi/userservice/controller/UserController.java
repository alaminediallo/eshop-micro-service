package com.lamine.isi.userservice.controller;

import com.lamine.isi.userservice.dto.UserMapper;
import com.lamine.isi.userservice.dto.UserDTO;
import com.lamine.isi.userservice.model.UserCredential;
import com.lamine.isi.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserCredential> users = userService.getAll();
        List<UserDTO> userDTOs = UserMapper.toUserDTOList(users);
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserCredential user = userService.getById(id);
        UserDTO userDTO = UserMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserCredential user = userService.getByUsername(username);
        UserDTO userDTO = UserMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserCredential user = UserMapper.toUser(userDTO);
        UserCredential updatedUser = userService.update(id, user);
        UserDTO updatedUserDTO = UserMapper.toUserDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        UserCredential updatedUser = userService.partialUpdate(id, updates);
        UserDTO updatedUserDTO = UserMapper.toUserDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}