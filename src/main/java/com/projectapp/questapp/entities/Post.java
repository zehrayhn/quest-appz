package com.projectapp.questapp.entities;

import javax.persistence.*;

import com.projectapp.questapp.entities.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Entity
@Table(name="post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //LAZY user objesini db deb hemen çekme
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id",nullable = false)
    //bir user silindiğinde user a ait tüm postlar silinir.
   @OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

  //  @Column(name="user_id")
    //Long userId;
    
    @Column(name="title")
    String title;
    
 //   @Column(columnDefinition = "clob")

 //   @Lob(type = LobType.CLOB)
 //   @Lob
    @Column(columnDefinition="text")
    String text;
}
