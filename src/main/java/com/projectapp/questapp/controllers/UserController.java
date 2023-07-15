package com.projectapp.questapp.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projectapp.questapp.entities.User;
import com.projectapp.questapp.requests.UserDeleteRequest;
import com.projectapp.questapp.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	

	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();}
	
	

	
	//@ResponseStatus(code=org.springframework.http.HttpStatus.CREATED)
	@PostMapping("/useradd")
	public User createUser(@RequestBody User newUser) {
		return userService.saveOneUser(newUser);
		
	}
	
	@GetMapping("/{userId}")
	public User getOneUser(@PathVariable long userId) {
		//custom exception
		//orelse(null)ıd bulamazsa null doner
		return userService.getOneUserById(userId);
	}
	
	@PutMapping("/{update}")
	public User updateOneUser(@PathVariable("userId") Long userId,@RequestBody User newUser) {
		
			return userService.updateOneUser(userId,newUser);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOneUser(@PathVariable Long id) {
		this.userService.deleteByIdd(id);
	}
	
}
	
	

