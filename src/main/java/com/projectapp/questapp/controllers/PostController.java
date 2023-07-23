package com.projectapp.questapp.controllers;

import com.projectapp.questapp.entities.Post;
import com.projectapp.questapp.requests.PostCreateRequest;
import com.projectapp.questapp.requests.PostUpdateRequest;
import com.projectapp.questapp.responses.PostResponse;
import com.projectapp.questapp.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
//(value="/posts", produces = "application/json")+

	private PostService postService;

	@Autowired
	public PostController(PostService postService){
		this.postService=postService;
	}


	@GetMapping
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){

		return postService.getAllPosts(userId);

	}
	@PostMapping
	public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
		return postService.createOnePost(newPostRequest);
	}

	//@GetMapping(value={"{/postId}"})
	@RequestMapping(
			value = "/postId",
			produces = "application/json",
			method = RequestMethod.GET)
	public Post getOnePost(@PathVariable(value="postId") Long postId){
		return postService.getOnePostById(postId);
	}




	@PutMapping("{postId}")
	public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost){
		return postService.updateOnePostById(postId,updatePost);
	}

	@DeleteMapping("{postId}")
	public void deleteOnePost(@PathVariable Long postId) {
		postService.deleteOnePostByIdd(postId);

	}
}
