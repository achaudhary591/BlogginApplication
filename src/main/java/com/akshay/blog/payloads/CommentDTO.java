package com.akshay.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {

    private Integer commentId;

    @NotNull
    private String content;
}
