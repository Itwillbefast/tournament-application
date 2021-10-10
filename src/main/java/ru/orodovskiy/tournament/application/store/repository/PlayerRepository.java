package ru.orodovskiy.tournament.application.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.orodovskiy.tournament.application.store.entity.PlayerEntity;

import java.util.stream.Stream;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    Stream<PlayerEntity> streamAllByCountryStartsWithIgnoreCase(String country);

    Stream<PlayerEntity> streamAllBy();
}
