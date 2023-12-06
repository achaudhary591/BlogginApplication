package com.akshay.blog.services;

import com.akshay.blog.payloads.UserDTO;

import java.util.List;

public interface UserServices {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, Integer  userId);
    UserDTO getuserById(Integer userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer userId);
}
