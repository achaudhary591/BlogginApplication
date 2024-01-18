package com.akshay.blog.services;

import com.akshay.blog.entities.Post;
import com.akshay.blog.payloads.PostDTO;
import com.akshay.blog.payloads.PostResponse;

import java.util.List;
import java.util.Set;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO, Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize);
    PostDTO getSinglePostById(Integer postId);
    List<PostDTO> getAllPostByCategory(Integer categoryId);
    List<PostDTO> getAllPostByUser(Integer userId);
    List<PostDTO> searchPosts(String keyword);
}
