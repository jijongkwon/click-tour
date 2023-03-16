package com.clicktour.clicktour.domain.board;

import com.clicktour.clicktour.domain.BaseTimeEntity;
import com.clicktour.clicktour.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Builder
    public Comments(Long id, String comment, Board board, Users user)
    {
        this.id = id;
        this.comment = comment;
        this.board = board;
        this.users = user;
    }

    public void update(String comment){
        this.comment =comment;
    }

    public boolean checkUser(String nickname){
        return this.users.getNickname().equals(nickname);
    }
}
