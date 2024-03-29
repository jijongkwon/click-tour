package com.clicktour.clicktour.domain.board.dto;

import com.clicktour.clicktour.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardDetailResponseDto {

    private String title;
    private String nickname;
    private String content;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private List<CommentResponseDto> commentResponseDtoList;

    public BoardDetailResponseDto(Board board) {
        this.title = board.getTitle();
        this.nickname = board.getUsers().getNickname();
        this.content = board.getContent();
        this.createdDate = board.getCreatedDate();
        this.modifiedDate = board.getModifiedDate();
        this.commentResponseDtoList = board.getComments().
                stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
