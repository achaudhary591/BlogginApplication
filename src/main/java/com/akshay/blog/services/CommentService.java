package com.akshay.blog.services;

import com.akshay.blog.payloads.CommentDTO;

import java.util.Set;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId);
    void deleteComment(Integer commentId);

    CommentDTO getSingleCommentUsingCommentId(Integer commentId);

    Set<CommentDTO> getAllComments();
}
