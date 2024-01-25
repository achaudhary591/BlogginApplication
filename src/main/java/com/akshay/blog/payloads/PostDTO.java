package com.akshay.blog.payloads;

import com.akshay.blog.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Integer postId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String imageName;

    private Date addedDate;
    private CategoryDTO category;
    private UserDTO user;

    private Set<CommentDTO> comments = new HashSet<>();
}
