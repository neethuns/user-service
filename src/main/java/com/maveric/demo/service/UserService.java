package com.maveric.demo.service;

import com.maveric.demo.dto.UserDto;

import com.maveric.demo.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers(Integer page, Integer pageSize);
    UserDto createUser(User user);
    UserDto getUserDetails(String userId);

    UserDto updateUser(User user, String userId);

    String deleteUser(String userId);

    UserDto userByEmail(String emailId);


}
