package ru.orodovskiy.tournament.application.store.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "coach")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String surname;

    @NonNull
    private Integer age;

    private String position;

    @NonNull
    private String category;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "football_team_id")
    private FootballTeamEntity footballTeam;
}
