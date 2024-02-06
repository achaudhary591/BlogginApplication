package com.akshay.blog.payloads;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
public class RoleDTO {
    @Id
    private Integer roleId;

    @NotNull
    private String role;
}
