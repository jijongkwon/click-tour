package com.clicktour.clicktour.domain.planner;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "planner_map")
public class PlannerMap {

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
    private Float x;

    @Column(nullable = false)
    private Float y;
}
