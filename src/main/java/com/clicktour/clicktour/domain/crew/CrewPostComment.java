package com.clicktour.clicktour.domain.crew;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "crew_post_comment")
public class CrewPostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "crew_post_id")
    private CrewPost crewPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
