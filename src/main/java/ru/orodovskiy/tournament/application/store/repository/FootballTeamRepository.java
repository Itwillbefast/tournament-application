package ru.orodovskiy.tournament.application.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;

import java.util.Optional;
import java.util.stream.Stream;

public interface FootballTeamRepository extends JpaRepository<FootballTeamEntity, Long> {

    Optional<FootballTeamEntity> findByName(String name);

    Stream<FootballTeamEntity> streamAllByNameStartsWithIgnoreCase(String prefixName);

    Stream<FootballTeamEntity> streamAllBy();
}
