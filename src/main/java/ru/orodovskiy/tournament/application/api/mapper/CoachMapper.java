package ru.orodovskiy.tournament.application.api.mapper;

import org.mapstruct.Mapper;
import ru.orodovskiy.tournament.application.api.dto.CoachDto;
import ru.orodovskiy.tournament.application.store.entity.CoachEntity;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    CoachDto toDto (CoachEntity coach);
}
