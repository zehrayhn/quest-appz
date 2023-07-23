package com.projectapp.questapp.controllers;

import com.projectapp.questapp.entities.User;
import com.projectapp.questapp.requests.UserDeleteRequest;
import com.projectapp.questapp.requests.UserRequest;
import com.projectapp.questapp.security.JwtTokenProvider;
import com.projectapp.questapp.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    private PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public String login(@RequestBody UserRequest loginRequest){
       UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());
        Authentication auth =authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
       String jwtToken= jwtTokenProvider.generateJwtToken(auth);
        return "Bearer " + jwtToken;
    }

   @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest registerRequest){
        if(userService.getOneUserByUserName(registerRequest.getUserName()) !=null)
            return new ResponseEntity<>("Username already in use.", HttpStatus.BAD_REQUEST);
        User user =new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.saveOneUser(user);
        return new ResponseEntity<>("User successfully registered.", HttpStatus.CREATED);
    }

}
