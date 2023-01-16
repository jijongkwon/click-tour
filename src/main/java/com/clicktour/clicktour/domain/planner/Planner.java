package com.clicktour.clicktour.domain.planner;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date start_date;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date end_date;

    @Column
    private String intro;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

}
