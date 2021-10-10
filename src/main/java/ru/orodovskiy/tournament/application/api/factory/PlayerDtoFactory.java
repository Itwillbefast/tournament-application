package ru.orodovskiy.tournament.application.api.factory;

import org.springframework.stereotype.Component;
import ru.orodovskiy.tournament.application.api.dto.PlayerDto;
import ru.orodovskiy.tournament.application.store.entity.PlayerEntity;

@Component
public class PlayerDtoFactory {

    public PlayerDto makeDefault (PlayerEntity entity) {

        return PlayerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .country(entity.getCountry())
                .age(entity.getAge())
                .build();
    }
}
