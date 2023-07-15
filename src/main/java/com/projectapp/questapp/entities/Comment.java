package com.projectapp.questapp.entities;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Entity
@Table(name="comment")
@Data
public class Comment {

	
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	
	//LAZY user objesini db deb hemen çekme
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="user_id")
    //bir user silindiğinde user a ait tüm postlar silinir.
   @OnDelete(action=OnDeleteAction.CASCADE)
   @JsonIgnore
   User user;
	
  //LAZY user objesini db deb hemen çekme
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id")
    //bir user silindiğinde user a ait tüm postlar silinir.
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore
    Post post;
  //@Column(name="post_id")
  //Long postId;

	//@Column(name="user_id")
	//Long userId;
	@Column(columnDefinition = "text")
	String text;
	
}
