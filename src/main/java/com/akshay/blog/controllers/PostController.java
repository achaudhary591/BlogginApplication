package com.akshay.blog.controllers;

import com.akshay.blog.payloads.CategoryDTO;
import com.akshay.blog.payloads.PostDTO;
import com.akshay.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    ///create post
    @PostMapping("/create-post/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);

        return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
    }

    ///get posts by user
    @GetMapping("/user/{userId}/all_posts")
    public ResponseEntity<List<PostDTO>> getAllPostByUser(@PathVariable Integer userId){

        List<PostDTO> allPosts = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<List<PostDTO>>(allPosts, HttpStatus.OK);
    }

    ///get post by category
    @GetMapping("/Category/{categoryId}/all_posts")
    public ResponseEntity<List<PostDTO>> getAllPostBy(@PathVariable Integer categoryId){

        List<PostDTO> allPosts = this.postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<List<PostDTO>>(allPosts, HttpStatus.OK);
    }
}
