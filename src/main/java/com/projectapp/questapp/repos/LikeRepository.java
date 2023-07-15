package com.projectapp.questapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectapp.questapp.entities.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>{

    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long aLong);

    List<Like> findByPostId(Long aLong);
}
