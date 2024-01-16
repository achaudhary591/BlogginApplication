package com.akshay.blog.services;

import com.akshay.blog.payloads.UserDTO;

import java.util.List;

public interface UserServices {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, Integer  userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer userId);

    List<UserDTO> createMultipleUsers(List<UserDTO> users);
}
