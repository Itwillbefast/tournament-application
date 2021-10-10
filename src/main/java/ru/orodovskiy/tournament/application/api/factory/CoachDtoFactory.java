package ru.orodovskiy.tournament.application.api.factory;

import org.springframework.stereotype.Component;
import ru.orodovskiy.tournament.application.api.dto.CoachDto;
import ru.orodovskiy.tournament.application.store.entity.CoachEntity;

@Component
public class CoachDtoFactory {

    public CoachDto makeDefault (CoachEntity entity) {

        return CoachDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .age(entity.getAge())
                .category(entity.getCategory())
                .position(entity.getPosition())
                .build();
    }
}
