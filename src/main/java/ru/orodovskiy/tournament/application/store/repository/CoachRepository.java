package ru.orodovskiy.tournament.application.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.orodovskiy.tournament.application.store.entity.CoachEntity;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<CoachEntity, Long> {

    Optional<CoachEntity> findByCategoryAndFootballTeamId(String category, Long footballTeamId);
}
