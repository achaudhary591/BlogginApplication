package com.akshay.blog.services;

import com.akshay.blog.entities.Post;
import com.akshay.blog.payloads.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO, Integer postId);
    void deletePost(Integer postId);
    List<PostDTO> getAllPost();
    PostDTO getSinglePostById(Integer postId);
    List<PostDTO> getAllPostByCategory(Integer categoryId);
    List<PostDTO> getAllPostByUser(Integer userId);
    List<PostDTO> searchPosts(String keyword);
}
