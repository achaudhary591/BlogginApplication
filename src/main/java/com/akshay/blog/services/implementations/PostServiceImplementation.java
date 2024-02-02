package com.akshay.blog.services.implementations;

import com.akshay.blog.entities.Category;
import com.akshay.blog.entities.Post;
import com.akshay.blog.entities.User;
import com.akshay.blog.exceptions.ResourceNotFoundException;
import com.akshay.blog.payloads.CategoryDTO;
import com.akshay.blog.payloads.PostDTO;
import com.akshay.blog.payloads.PostResponse;
import com.akshay.blog.reporsitories.CategoryRepository;
import com.akshay.blog.reporsitories.PostRepository;
import com.akshay.blog.reporsitories.UserRepository;
import com.akshay.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * @param postDTO
     * @return
     */
    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "User Id ", userId));

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id ", categoryId));

        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepository.save(post);

        return this.modelMapper.map(newPost, PostDTO.class);
    }

    /**
     * @param postDTO
     * @param postId
     * @return
     */
    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ", "Post ID", postId));

        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setImageName(postDTO.getImageName());
        post.setAddedDate(postDTO.getAddedDate());

        Post updatedPost = this.postRepository.save(post);

        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    /**
     * @param postId
     */
    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ", "Post ID", postId));
        this.postRepository.delete(post);
    }

    /**
     * @return
     */
    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        ///page number start from 0 by default

        if(pageNumber <= 0){
            pageNumber = 1;
        }
        if (pageSize < 1){
            pageSize = 1;
        }
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of((pageNumber - 1) ,pageSize, sort);

        Page<Post> pagePost = this.postRepository.findAll(pageable);
        List<Post> allPosts = pagePost.getContent();

        List<PostDTO> postDTOList = allPosts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOList);
        postResponse.setPageNumber(pagePost.getNumber() + 1);
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    /**
     * @param postId
     * @return
     */
    @Override
    public PostDTO getSinglePostById(Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ", "Post ID", postId));

        return this.modelMapper.map(post, PostDTO.class);
    }

    /**
     * @param categoryId
     * @return
     */
    @Override
    public List<PostDTO> getAllPostByCategory(Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id ", categoryId));
        List<Post> postList = this.postRepository.findByCategory(category);
        return postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<PostDTO> getAllPostByUser(Integer userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "User Id ", userId));
        List<Post> postList = this.postRepository.findByUser(user);
        return postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    /**
     * @param keyword
     * @return search post by title
     */
    @Override
    public List<PostDTO> searchPostsByTitle(String keyword) {

        List<Post> postList = this.postRepository.findByTitleContains(keyword);
        List<PostDTO> postDTOList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        //another method shown in video
        /*List<Post> postList = this.postRepository.searchByTitle("%"+keyword+"%");
        List<PostDTO> postDTOList = postList.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());*/
        return postDTOList;
    }
}
