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
    private String loginId;

    @Column(nullable = false)
    private String loginPassword;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;

    @Column
    private String address;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean checkId(String id) {
        return this.loginId.equals(id);
    }

    public boolean checkNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public boolean checkEmail(String email) {
        return this.email.equals(email);
    }
}
