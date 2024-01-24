package com.akshay.blog.services.implementations;

import com.akshay.blog.entities.Comment;
import com.akshay.blog.entities.Post;
import com.akshay.blog.entities.User;
import com.akshay.blog.exceptions.ResourceNotFoundException;
import com.akshay.blog.payloads.CommentDTO;
import com.akshay.blog.reporsitories.CommentRepository;
import com.akshay.blog.reporsitories.PostRepository;
import com.akshay.blog.reporsitories.UserRepository;
import com.akshay.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param commentDTO
     * @param postId
     * @return
     */
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", " post_id ", postId));

        Comment comment = this.modelMapper.map(commentDTO, Comment.class);

        comment.setPost(post);

        Comment savedComment= this.commentRepository.save(comment);

        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    /**
     * @param commentId 
     */
    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment ", " comment_id ", commentId));

        this.commentRepository.delete(comment);

    }
}
