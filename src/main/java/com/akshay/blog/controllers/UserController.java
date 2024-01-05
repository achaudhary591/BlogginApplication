package com.akshay.blog.controllers;

import com.akshay.blog.payloads.ApiResponse;
import com.akshay.blog.payloads.UserDTO;
import com.akshay.blog.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/create-user")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = this.userServices.createUser(userDTO);

        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Integer uid) {
        UserDTO updatedUser = this.userServices.updateUser(userDTO, uid);
        return  ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        this.userServices.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllusers(){
        return ResponseEntity.ok(this.userServices.getAllUsers());
    }

    @GetMapping("/get-user-detail/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userServices.getuserById(userId) );
    }
}
