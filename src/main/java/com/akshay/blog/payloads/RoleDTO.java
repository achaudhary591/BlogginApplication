package com.akshay.blog.payloads;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
public class RoleDTO {

    private Integer roleId;

    private String role;
}
