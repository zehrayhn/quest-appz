package com.projectapp.questapp.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.projectapp.questapp.entities.User;
import com.projectapp.questapp.repos.UserRepository;
import com.projectapp.questapp.requests.UserDeleteRequest;

import lombok.AllArgsConstructor;

@Service
public class UserService {

	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}

	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	public User saveOneUser(User newUser) {
		// TODO Auto-generated method stub
		return this.userRepository.save(newUser);
	}

	public User getOneUserById(Long userId) {
		return this.userRepository.findById(userId).orElse(null);
	}

	public User updateOneUser(Long userId, User newUser) {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent()) {
			User foundUser=user.get();
			foundUser.setUserName(newUser.getUserName());
			foundUser.setPassword(newUser.getPassword());
			//foundUser.setId(newUser.getId());
			userRepository.save(foundUser);
		
//			//foundUser.setId(newUser.getId());
		
			userRepository.save(foundUser);
			return foundUser;
			
		}
		else
		    return null;
	}


	public void deleteByIdd(Long id) {
		this.userRepository.deleteById(id);
		
	}

	
}
