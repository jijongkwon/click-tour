package com.clicktour.clicktour.domain.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String id_login;

    @Column(nullable = false)
    private String password_login;

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

    @Column(columnDefinition = "TEXT")
    private String intro;

    @Column
    private String picture;
}