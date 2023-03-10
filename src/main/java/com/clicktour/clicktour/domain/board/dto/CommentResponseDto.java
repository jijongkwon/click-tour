package com.clicktour.clicktour.domain.board.dto;

import com.clicktour.clicktour.domain.board.Comments;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private String comment;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private String nickname;

    public CommentResponseDto(Comments comments){
        this.comment = comments.getComment();
        this.createdDate = comments.getCreatedDate();
        this.modifiedDate = comments.getModifiedDate();
        this.nickname = comments.getUsers().getNickname();
    }
}
