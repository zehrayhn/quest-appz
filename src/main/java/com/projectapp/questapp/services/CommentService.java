package com.projectapp.questapp.services;

import com.projectapp.questapp.entities.Comment;
import com.projectapp.questapp.entities.Post;
import com.projectapp.questapp.entities.User;
import com.projectapp.questapp.repos.CommentRepository;
import com.projectapp.questapp.requests.CommentCreateRequest;
import com.projectapp.questapp.requests.CommentUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent()
        && postId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());

        } else if(userId.isPresent()){
            return commentRepository.findByUserId(userId.get());
        } else if(postId.isPresent()){
            return commentRepository.findByPostId(postId.get());
        }
            return commentRepository.findAll();

    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {

        User user=userService.getOneUserById(request.getUserId());
        Post post=postService.getOnePostById(request.getPostId());

        if(user!=null&& post!=null){
            Comment commentToSave=new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            return commentRepository.save(commentToSave);

        }else
        return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment=commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment commentToUpdate=comment.get();
            commentToUpdate.setText(request.getText());
            return commentRepository.save(commentToUpdate);
        }else
            return null;

    }

    public void deleteOneCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
