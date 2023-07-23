package com.projectapp.questapp.repos;



import org.springframework.data.jpa.repository.JpaRepository;

import com.projectapp.questapp.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}
