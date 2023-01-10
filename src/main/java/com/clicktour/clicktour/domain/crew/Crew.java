package com.clicktour.clicktour.domain.crew;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int size;

    @Column
    private String intro;

    @Column
    private String recruit;

    @Column
    private String main_image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
