package com.example.shopapp.service.impl;

import com.example.shopapp.dto.UserDto;
import com.example.shopapp.mapping.UserMapper;
import com.example.shopapp.model.User;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static UserRepository userRepository;
    @Override
    public UserDto register(UserDto userDto) {
        String phoneNumber = userDto.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        User user = UserMapper.mapToUser(userDto);
        User newUser = userRepository.save(user);
        return UserMapper.mapToUserDto(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return null;
    }
}
