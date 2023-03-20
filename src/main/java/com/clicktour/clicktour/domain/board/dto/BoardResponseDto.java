package com.clicktour.clicktour.domain.board.dto;

import com.clicktour.clicktour.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private String title;
    private String nickname;
    private int view;
    private LocalDate createdDate;
    private LocalDate modifiedDate;


    public BoardResponseDto(Board board){
        this.title = board.getTitle();
        this.nickname = board.getUsers().getNickname();
        this.view = board.getView();
        this.createdDate = board.getCreatedDate();
        this.modifiedDate = board.getModifiedDate();
    }
}
