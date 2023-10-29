package com.example.shopapp.service;

import com.example.shopapp.dto.UserDto;

public interface UserService {
    UserDto register(UserDto userDto);
    String login(String phoneNumber, String password);
}
