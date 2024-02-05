package com.akshay.blog.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    private Integer roleId;

    @NotNull
    private String role;
}