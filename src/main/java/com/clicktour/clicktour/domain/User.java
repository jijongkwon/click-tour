package com.clicktour.clicktour.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nick_name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String Gender;

    @Column
    private String address;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String intro;

}
