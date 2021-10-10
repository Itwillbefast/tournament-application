package ru.orodovskiy.tournament.application.api.factory;

import org.springframework.stereotype.Component;
import ru.orodovskiy.tournament.application.api.dto.FootballTeamDto;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;

@Component
public class FootballTeamDtoFactory {

    public FootballTeamDto makeDefault (FootballTeamEntity entity) {

        return FootballTeamDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .country(entity.getCountry())
                .budget(entity.getBudget())
                .stadiumCapacity(entity.getStadiumCapacity())
                .build();
    }
}
