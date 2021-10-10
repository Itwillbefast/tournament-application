package ru.orodovskiy.tournament.application.store.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "football_team")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FootballTeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String country;

    @NonNull
    private Long budget;

    private Long stadiumCapacity;

    @OneToMany
    @JoinColumn(name = "football_team_id")
    private List<PlayerEntity> players = new ArrayList<>(23);

    @OneToMany
    @JoinColumn(name = "football_team_id")
    List<CoachEntity> coachesList = new ArrayList<>();
}
