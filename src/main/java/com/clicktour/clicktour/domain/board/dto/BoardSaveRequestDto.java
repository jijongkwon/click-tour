package com.clicktour.clicktour.domain.board.dto;

import com.clicktour.clicktour.domain.board.Board;
import com.clicktour.clicktour.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private String nickname;
    private Users users;

    @Builder
    public BoardSaveRequestDto(String title, String content, String nickname){
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .users(users)
                .build();
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
