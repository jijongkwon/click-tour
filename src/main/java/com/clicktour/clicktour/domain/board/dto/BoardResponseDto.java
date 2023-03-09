package com.clicktour.clicktour.domain.board.dto;

import com.clicktour.clicktour.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private String title;
    private String nickname;
    private int view;

    public BoardResponseDto(Board board){
        this.title = board.getTitle();
        this.nickname = board.getUsers().getNickname();
        this.view = board.getView();
    }
}
