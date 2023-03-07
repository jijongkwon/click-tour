package com.clicktour.clicktour.domain.planner;

import com.clicktour.clicktour.domain.BaseTimeEntity;
import com.clicktour.clicktour.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "planner")
public class Planner extends BaseTimeEntity {

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

    @Column
    private String concept;

    @Column(nullable = false)
    private String visibility;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "planner", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Plan> planList;

    @OneToMany(mappedBy = "planner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Place> placeList;

    @Builder
    public Planner(String title, Date start_date, Date end_date, String intro, String concept, String visibility,
                   Users users, List<Plan> planList, List<Place> placeList) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.intro = intro;
        this.concept = concept;
        this.visibility = visibility;
        this.users = users;
        this.planList = planList;
        this.placeList = placeList;
    }

    public void update(String title, Date start_date, Date end_date, String intro,
                       String concept, String visibility){
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.intro = intro;
        this.concept = concept;
        this.visibility = visibility;
    }
}
