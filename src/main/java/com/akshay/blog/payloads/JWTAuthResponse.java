package com.akshay.blog.payloads;

import lombok.Data;

@Data
public class JWTAuthResponse {

    private String token;

    private UserDTO user;
}
