package com.example.shopapp.mapping;

import com.example.shopapp.dto.UserDto;
import com.example.shopapp.model.Role;
import com.example.shopapp.model.User;
import com.example.shopapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserMapper {
    private static RoleRepository roleRepository;
    public static User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setAddress(userDto.getAddress());
         user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setFacebookAccountId(userDto.getFacebookAccountId());
        user.setGoogleAccountId(userDto.getGoogleAccountId());
        Role role =  roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Cannot find role with Id: " + userDto.getRoleId()));
        user.setRole(role);
        return user;
    }
    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAddress(user.getAddress());
        userDto.setPassword(user.getPassword());
        userDto.setFullName(user.getFullName());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setFacebookAccountId(user.getFacebookAccountId());
        userDto.setGoogleAccountId(user.getGoogleAccountId());
        userDto.setRoleId(user.getRole().getId());
        return userDto;
    }
}
