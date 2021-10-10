package ru.orodovskiy.tournament.application.api.dto;

import lombok.*;
import ru.orodovskiy.tournament.application.store.entity.PlayerEntity;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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
