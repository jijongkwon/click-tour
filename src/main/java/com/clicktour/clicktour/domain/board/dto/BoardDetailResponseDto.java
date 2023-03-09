package com.clicktour.clicktour.domain.board.dto;

import com.clicktour.clicktour.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDetailResponseDto {

    private String title;
    private String nickname;
    private String content;

    public BoardDetailResponseDto(Board board){
        this.title = board.getTitle();
        this.nickname = board.getUsers().getNickname();
        this.content = board.getContent();
    }
}
