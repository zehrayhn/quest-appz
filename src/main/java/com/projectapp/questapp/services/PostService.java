package com.projectapp.questapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import com.projectapp.questapp.entities.Like;
import com.projectapp.questapp.requests.PostUpdateRequest;
import com.projectapp.questapp.entities.Post;
import com.projectapp.questapp.repos.PostRepository;
import com.projectapp.questapp.requests.PostCreateRequest;
import com.projectapp.questapp.responses.LikeResponse;
import com.projectapp.questapp.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectapp.questapp.entities.User;
import lombok.AllArgsConstructor;

@Service
public class PostService {
 
	private PostRepository postRepository;
	private LikeService likeService;
    private UserService userService;


	//@Autowired
	public PostService(PostRepository postRepository, UserService userService){
		this.postRepository= postRepository;
		this.userService=userService;
	//	this.likeService=likeService;
	}

	@Autowired
	public void setLikeService(LikeService likeService){
	this.likeService=likeService;
	}
	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		List<Post> list;
		if(userId.isPresent())
			list=postRepository.findByUserId(userId.get());
		else
		list =postRepository.findAll();
		return list.stream().map(p->{
		List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.empty(), Optional.of(p.getId()));
			return new PostResponse(p,likes);}).collect(Collectors.toList()); // bir tane post alıcak ve new postresponse un içerisne vericek
//her bir post için gidip onun likelarını cekiyorum ve postresponse objesi oluşturuken de o like ları bu objenin içerisine koyuyorum.

	}



	public Post createOnePost(PostCreateRequest newPostRequest) {
		User user=userService.getOneUserById(newPostRequest.getUserId());
		if(user == null)
			return null;
		else;
		Post toSave=new Post();
		toSave.setId(newPostRequest.getId());
		toSave.setText(newPostRequest.getText());
		toSave.setTitle(newPostRequest.getTitle());
		toSave.setUser(user);
		return postRepository.save(toSave);
		
		
	}
	
	public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
		Optional<Post> post=postRepository.findById(postId);
	//
	//	User user=userService.getOneUser(updatePost.getUserId());
		if(post.isPresent()) {
			Post toUpdate=post.get();
			toUpdate.setText(updatePost.getText());
			toUpdate.setTitle(updatePost.getTitle());
		//	toUpdate.setUser(post);
			postRepository.save(toUpdate);
			return toUpdate;
		}
		else;
		return null;
//	
//		if(post==null)
//			return null;
//		else;
//		Post toUpdate=post new Post;
//		toUpdate.setText(updatePost.getText());
////		toUpdate.setTitle(updatePost.getTitle());
////		toUpdate.setUser(post);
////		postRepository.save(toUpdate);
////		return toUpdate;
		
	}

	public void deleteOnePostByIdd(Long postId) {
		this.postRepository.deleteById(postId);

		
	}

	public Post getOnePostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}
}
