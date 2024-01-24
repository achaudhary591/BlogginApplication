package com.akshay.blog.controllers;

import com.akshay.blog.config.AppConstants;
import com.akshay.blog.entities.Post;
import com.akshay.blog.payloads.ApiResponse;
import com.akshay.blog.payloads.CategoryDTO;
import com.akshay.blog.payloads.PostDTO;
import com.akshay.blog.payloads.PostResponse;
import com.akshay.blog.services.FileService;
import com.akshay.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    ///create post
    @PostMapping("/create-post/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);

        return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
    }

    ///single post by id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostByPostId(@PathVariable Integer postId){
        PostDTO postDTO = this.postService.getSinglePostById(postId);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    ///update post
    @PutMapping("/update-post/{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postId){

        PostDTO updatedPost = this.postService.updatePost(postDTO, postId);

        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
    }

    ///delete post
    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
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

    ///all posts
    @GetMapping("/all")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
            ){
        PostResponse allPosts = this.postService.getAllPost(pageNumber - 1, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(allPosts, HttpStatus.OK);
    }

    ///search
    @GetMapping("/search/{keywords}")
    public  ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords){

       List<PostDTO> postDTOList = this.postService.searchPostsByTitle(keywords);
       return new ResponseEntity<List<PostDTO>>(postDTOList, HttpStatus.OK);
    }

    ///post image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {

        PostDTO postDTO = this.postService.getSinglePostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDTO.setImageName(fileName);
        PostDTO updatedPostDTO = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatedPostDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadPostImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
