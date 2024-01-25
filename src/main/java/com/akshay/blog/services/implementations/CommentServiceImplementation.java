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

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param userId
     * @return
     */
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", " post_id ", postId));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " user_id ", userId));

        Comment comment = this.modelMapper.map(commentDTO, Comment.class);

        comment.setPost(post);
        comment.setUser(user);

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

    /**
     * @param commentId 
     * @return
     */
    @Override
    public CommentDTO getSingleCommentUsingCommentId(Integer commentId) {

        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", " comment_id", commentId));

        return this.modelMapper.map(comment, CommentDTO.class);
    }

    /**
     * @return 
     */
    @Override
    public Set<CommentDTO> getAllComments() {

        List<Comment> commentList = this.commentRepository.findAll();

        return commentList.stream().map(comment -> this.modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toSet());
    }
}
