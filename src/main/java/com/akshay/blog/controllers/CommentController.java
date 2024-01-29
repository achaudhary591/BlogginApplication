package com.akshay.blog.controllers;

import com.akshay.blog.payloads.ApiResponse;
import com.akshay.blog.payloads.CommentDTO;
import com.akshay.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    ///Creating comment and assigning it to post and a user using postid and userid
    @PostMapping("/create/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postId, @PathVariable Integer userId){
       CommentDTO createdComment = this.commentService.createComment(commentDTO, postId, userId);

       return new ResponseEntity<CommentDTO>(createdComment, HttpStatus.CREATED);
    }

    ///fetch all comment
    @GetMapping("/all-comments")
    public ResponseEntity<Set<CommentDTO>> getAllComments(){
        Set<CommentDTO> allComments = this.commentService.getAllComments();
        return new ResponseEntity<Set<CommentDTO>>(allComments, HttpStatus.OK);
    }


    ///Deleting comment using comment id
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("comment is deleted successfully!!", true), HttpStatus.OK);
    }
}
