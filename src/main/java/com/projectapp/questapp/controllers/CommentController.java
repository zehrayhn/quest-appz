package com.projectapp.questapp.controllers;

import com.projectapp.questapp.entities.Comment;
import com.projectapp.questapp.requests.CommentCreateRequest;
import com.projectapp.questapp.requests.CommentUpdateRequest;
import com.projectapp.questapp.services.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    @GetMapping
    public List<Comment> getAllComments(@RequestParam(value="userId") Optional<Long> userId,
                                         @RequestParam(value="postId") Optional<Long> postId){
        return commentService.getAllCommentsWithParam(userId,postId);

    }

  //  @GetMapping("/commentId")
    @RequestMapping(value="/commentId",method=RequestMethod.GET)
    public Comment getOneComment(@RequestParam(value="commentId") Long commentId){
        return commentService.getOneCommentById(commentId);

    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest request){

        return commentService.createOneComment(request);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@RequestParam Long commentId, @RequestBody CommentUpdateRequest request){
        return commentService.updateOneCommentById(commentId,request);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteOneCommentById(commentId);
    }
}
