package ru.orodovskiy.tournament.application.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orodovskiy.tournament.application.api.exception.BadRequestException;
import ru.orodovskiy.tournament.application.api.exception.NotFoundException;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;
import ru.orodovskiy.tournament.application.store.repository.FootballTeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FootballTeamService {

    private final FootballTeamRepository footballTeamRepository;

    @Transactional
    public List<FootballTeamEntity> getFootballTeamsWithOrWithoutPrefix (Optional<String> prefixName) {

        Stream<FootballTeamEntity> footballTeamStream = prefixName
                .map(footballTeamRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(footballTeamRepository::streamAllBy);

        return footballTeamStream.collect(Collectors.toList());
    }

    @Transactional
    public FootballTeamEntity createFootballTeam(FootballTeamEntity footballTeam) {

        footballTeamRepository.findByName(footballTeam.getName())
                .ifPresent(footballTeamEntity -> {
                    throw new BadRequestException(String.format("Football team with name \"%s\" already exists", footballTeam.getName()));
                });

        if(footballTeam.getBudget() < 50_000_000) {
            throw new BadRequestException("Your club's budget is not enough to join the tournament");
        }
        if (footballTeam.getStadiumCapacity() < 30000) {
            throw new BadRequestException("Your stadium capacity is not enough to join the tournament");
        }

        footballTeamRepository.saveAndFlush(footballTeam);

        return footballTeam;
    }

    @Transactional
    public FootballTeamEntity updateFootballTeam(
            Long footballTeamId,
            FootballTeamEntity updatedFootballTeam) {

        if (updatedFootballTeam.getName().trim().isEmpty()) {
            throw new BadRequestException("Football team name can't be empty.");
        }

        if(updatedFootballTeam.getBudget() < 50_000_000) {
            throw new BadRequestException("Your club's budget is not enough to join the tournament");
        }
        if (updatedFootballTeam.getStadiumCapacity() < 30000) {
            throw new BadRequestException("Your stadium capacity is not enough to join the tournament");
        }

        FootballTeamEntity footballTeam = footballTeamRepository
                .findById(footballTeamId)
                .orElseThrow(() -> new NotFoundException(String.format("Football team with id \"%s\" is not found", footballTeamId)));

        footballTeam.setName(updatedFootballTeam.getName());
        footballTeam.setCountry(updatedFootballTeam.getCountry());
        footballTeam.setBudget(updatedFootballTeam.getBudget());
        footballTeam.setStadiumCapacity(updatedFootballTeam.getStadiumCapacity());

        footballTeamRepository.saveAndFlush(footballTeam);

        return footballTeam;
    }

    @Transactional
    public void deleteFootballTeam( Long footballTeamId) {

         footballTeamRepository.deleteById(footballTeamId);
    }
}
