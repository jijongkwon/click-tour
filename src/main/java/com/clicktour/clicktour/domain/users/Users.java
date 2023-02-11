package com.clicktour.clicktour.domain.users;

import com.clicktour.clicktour.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "users")
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login_id;

    @Column(nullable = false)
    private String login_password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
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

    @Enumerated(EnumType.STRING)
    private Role role;
}
