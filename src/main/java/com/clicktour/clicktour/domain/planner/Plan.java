package com.clicktour.clicktour.domain.planner;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "planner_id")
    private Planner planner;

    @Column(nullable = false)
    private String name;

    @Column
    private String memo;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @Builder
    public Plan(String name, String memo, Date date, Float x, Float y, Planner planner) {
        this.name = name;
        this.memo = memo;
        this.date = date;
        this.x = x;
        this.y = y;
        this.planner = planner;
    }

    public void update(String name, String memo, Date date, Float x, Float y){
        this.name = name;
        this.memo = memo;
        this.date = date;
        this.x = x;
        this.y = y;
    }
}
