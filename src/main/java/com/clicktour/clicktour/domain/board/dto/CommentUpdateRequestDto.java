package com.clicktour.clicktour.domain.board.dto;

import com.clicktour.clicktour.domain.board.Comments;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String content;

    public CommentUpdateRequestDto(Comments comments) {
        this.content = comments.getComment();
    }
}
