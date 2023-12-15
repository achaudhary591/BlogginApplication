package com.akshay.blog.controllers;

import com.akshay.blog.payloads.UserDTO;
import com.akshay.blog.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = this.userServices.createUser(userDTO);

        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }
}
