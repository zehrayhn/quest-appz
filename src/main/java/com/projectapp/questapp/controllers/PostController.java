package com.projectapp.questapp.controllers;

import com.projectapp.questapp.entities.Post;
import com.projectapp.questapp.requests.PostCreateRequest;
import com.projectapp.questapp.requests.PostUpdateRequest;
import com.projectapp.questapp.responses.PostResponse;
import com.projectapp.questapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
//(value="/posts", produces = "application/json")+
    @Autowired
	private PostService postService;

	public PostController(PostService postService){
		this.postService=postService;
	}

	private  void handleBusinessLogic() throws Exception {
		throw new Exception("Error");
	}
//	@GetMapping("{/userId}")
	@RequestMapping(
			value = "/userId",
			produces = "application/json",
			method = RequestMethod.GET)
	public List<PostResponse> getAllPosts(@PathVariable(value="userId") Optional<Long> userId){

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
