package com.clicktour.clicktour.domain.board.dto;

import com.clicktour.clicktour.domain.board.Board;
import com.clicktour.clicktour.domain.board.Comments;
import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
    private Long id;
    private String comment;
    private Users users;
    private Board board;

    public Comments toEntity(){
        Comments comments = Comments.builder()
                .id(id)
                .comment(comment)
                .user(users)
                .board(board)
                .build();
        return comments;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
