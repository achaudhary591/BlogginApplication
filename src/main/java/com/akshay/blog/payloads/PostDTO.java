package com.akshay.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Integer postId;
    private String title;

    private String content;

    private String imageName;

    private Date addedDate;
    private CategoryDTO category;
    private UserDTO user;
}
