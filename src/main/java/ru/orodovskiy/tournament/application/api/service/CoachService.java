package ru.orodovskiy.tournament.application.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.CoachDto;
import ru.orodovskiy.tournament.application.api.exception.BadRequestException;
import ru.orodovskiy.tournament.application.api.exception.NotFoundException;
import ru.orodovskiy.tournament.application.api.mapper.CoachMapper;
import ru.orodovskiy.tournament.application.store.entity.CoachEntity;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;
import ru.orodovskiy.tournament.application.store.repository.CoachRepository;
import ru.orodovskiy.tournament.application.store.repository.FootballTeamRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final FootballTeamRepository footballTeamRepository;

    private final CoachRepository coachRepository;

    private final CoachMapper coachMapper;

    @Transactional
    public List<CoachDto> getCoaches(Long footballTeamId) {

        FootballTeamEntity footballTeam = footballTeamRepository
                .findById(footballTeamId)
                .orElseThrow(()-> new NotFoundException(String.format("Football team with id \"%d\" not found", footballTeamId)));

        return footballTeam.getCoachesList().stream().map(coachMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public CoachDto createCoach(Long footballTeamId, CoachEntity coach) {

        FootballTeamEntity footballTeam = footballTeamRepository.findById(footballTeamId)
                .orElseThrow(() -> new NotFoundException(String.format("Football team with id \"%d\" not found", footballTeamId)));

        if (footballTeam.getCoachesList().size() > 1) {
            throw new BadRequestException("Too many coaches in your team! One football team must have one main coach and one coach assistant");
        }

        coachRepository
                .findByCategoryAndFootballTeamId(coach.getCategory(), footballTeamId)
                .filter(presentCoach -> Objects.equals(presentCoach.getCategory(), coach.getCategory()))
                .ifPresent(presentCoach -> {
                    throw new BadRequestException("You don't have two coaches with the same category");
                });

        coach.setFootballTeam(footballTeam);

        coachRepository.saveAndFlush(coach);

        if (coach.getCategory().equals("PRO")) {
            coach.setPosition("Main Coach");
        } else {
            coach.setPosition("Coach Assistant");
        }
        coachRepository.saveAndFlush(coach);

        return coachMapper.toDto(coach);
    }

    @Transactional
    public AckDto deleteCoach(Long coachId){

        coachRepository.deleteById(coachId);

        return AckDto.builder()
                .answer(true)
                .build();
    }
}
