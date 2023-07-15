package com.projectapp.questapp.controllers;

import com.projectapp.questapp.entities.Like;
import com.projectapp.questapp.requests.LikeCreateRequest;
import com.projectapp.questapp.responses.LikeResponse;
import com.projectapp.questapp.services.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
@AllArgsConstructor
public class LikeController {
    private LikeService likeService;


    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){

        return likeService.getAllLikesWithParam(userId,postId);
    }

    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest request){

        return likeService.createOneLike(request);
    }

    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId){

        return likeService.getOneLikeById(likeId);
    }
    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId){
        likeService.deleteOneLikeById(likeId);
    }


}
