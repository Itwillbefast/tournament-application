package ru.orodovskiy.tournament.application.api.mapper;

import org.mapstruct.Mapper;
import ru.orodovskiy.tournament.application.api.dto.FootballTeamDto;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;

@Mapper(componentModel = "spring")
public interface FootballTeamMapper {

    FootballTeamDto toDto (FootballTeamEntity footballTeam);
}
