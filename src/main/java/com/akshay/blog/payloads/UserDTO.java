package com.akshay.blog.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
