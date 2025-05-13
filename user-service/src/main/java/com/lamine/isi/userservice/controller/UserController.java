package com.lamine.isi.userservice.controller;

import com.lamine.isi.userservice.dto.UserMapper;
import com.lamine.isi.userservice.dto.UserDTO;
import com.lamine.isi.userservice.model.User;
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
        List<User> users = userService.getAll();
        List<UserDTO> userDTOs = UserMapper.toUserDTOList(users);
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        UserDTO userDTO = UserMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        UserDTO userDTO = UserMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = UserMapper.toUser(userDTO);
        User savedUser = userService.save(user);
        UserDTO savedUserDTO = UserMapper.toUserDTO(savedUser);
        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = UserMapper.toUser(userDTO);
        User updatedUser = userService.update(id, user);
        UserDTO updatedUserDTO = UserMapper.toUserDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User updatedUser = userService.partialUpdate(id, updates);
        UserDTO updatedUserDTO = UserMapper.toUserDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}