package ru.orodovskiy.tournament.application.api.mapper;

import org.mapstruct.Mapper;
import ru.orodovskiy.tournament.application.api.dto.PlayerDto;
import ru.orodovskiy.tournament.application.store.entity.PlayerEntity;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerDto toDto(PlayerEntity player);
}
