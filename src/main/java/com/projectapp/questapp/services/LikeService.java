package com.projectapp.questapp.services;

import com.projectapp.questapp.entities.Like;
import com.projectapp.questapp.entities.Post;
import com.projectapp.questapp.entities.User;
import com.projectapp.questapp.repos.LikeRepository;
import com.projectapp.questapp.requests.LikeCreateRequest;
import com.projectapp.questapp.responses.LikeResponse;
import com.projectapp.questapp.responses.PostResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, @Lazy PostService postService
                       ) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {

        List<Like> list;
        if(userId.isPresent()&&postId.isPresent()) {
            list= likeRepository.findByUserIdAndPostId(userId.get(),postId.get());

        }else if(userId.isPresent()){
            list= likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()){
            list= likeRepository.findByPostId(postId.get());
        }else
            list= likeRepository.findAll();
        return list.stream().map(l -> new LikeResponse(l)).collect((Collectors.toList()));
    //        list=likeRepository.findAll();
  //      return list.stream().map(like->new Like"Response(like)).collect(Collectors.toList());
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user=userService.getOneUserById(request.getUserId());
        Post post=postService.getOnePostById(request.getPostId());

        if(user!=null&&post!=null){
        Like likeToSave=new Like();
        likeToSave.setId(request.getId());
        likeToSave.setUser(user);
        likeToSave.setPost(post);
        return likeRepository.save(likeToSave);
        }else
        return null;


    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public void deleteOneLikeById(Long likeId){
        likeRepository.deleteById(likeId);
    }
}
