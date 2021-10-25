package ru.orodovskiy.tournament.application.api.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FootballTeamDto {

    private Long id;

    @NonNull
    private String name;

    private String country;

    @NonNull
    private Long budget;

    private Long stadiumCapacity;
}
