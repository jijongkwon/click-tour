package com.clicktour.clicktour.domain.planner;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String place;

    @ManyToOne
    @JoinColumn(name = "planner_id")
    private Planner planner;

    @Builder
    public Place(String place, Planner planner) {
        this.place = place;
        this.planner = planner;
    }

    public void update(String place) {
        this.place = place;
    }
}
