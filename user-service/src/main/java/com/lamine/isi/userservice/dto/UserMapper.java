package com.lamine.isi.userservice.dto;

import com.lamine.isi.userservice.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    // Conversion de User en UserDTO
    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail());
    }

    // Conversion de UserDTO en User (pour la création/mise à jour)
    public static User toUser(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    // Conversion d'une liste de User en liste de UserDTO
    public static List<UserDTO> toUserDTOList(List<User> users) {
        if (users == null) {
            return List.of();
        }
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}