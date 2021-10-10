package ru.orodovskiy.tournament.application.store.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "players")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String surname;

    @NonNull
    private String country;

    @NonNull
    private Integer age;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "football_team_id")
    private FootballTeamEntity footballTeam;
}
