package com.akshay.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class  UserDTO {

    private int id;

    @NotNull
    private String name;

    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String about;
}
