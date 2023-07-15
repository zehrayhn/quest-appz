package com.projectapp.questapp.responses;

import com.projectapp.questapp.entities.Like;
import lombok.Data;

@Data
public class LikeResponse {
    Long id;
    Long userId;
    Long postId;

    //constructor şeklinde mapper tanımladık
    public LikeResponse(Like entity){
        this.id= entity.getId();
        this.userId=entity.getUser().getId();
        this.postId=entity.getPost().getId();


    }

}
