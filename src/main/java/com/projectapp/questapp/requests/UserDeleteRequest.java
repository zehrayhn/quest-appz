package com.projectapp.questapp.requests;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteRequest {

	Long id;
	 String userName;	
	String password;
}
