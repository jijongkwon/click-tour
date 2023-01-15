package com.clicktour.clicktour.domain.review;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ReviewPostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_post_id")
    private ReviewPost reviewPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

}
