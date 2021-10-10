package ru.orodovskiy.tournament.application.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.FootballTeamDto;
import ru.orodovskiy.tournament.application.api.exception.BadRequestException;
import ru.orodovskiy.tournament.application.api.exception.NotFoundException;
import ru.orodovskiy.tournament.application.api.factory.FootballTeamDtoFactory;
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

    private final FootballTeamDtoFactory footballTeamDtoFactory;

    public List<FootballTeamDto> getFootballTeams (Optional<String> prefixName) {

        Stream<FootballTeamEntity> footballTeamStream = prefixName
                .map(footballTeamRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(footballTeamRepository::streamAllBy);

        return footballTeamStream.map(footballTeamDtoFactory::makeDefault).collect(Collectors.toList());
    }

    public FootballTeamDto createFootballTeam(
            String footballTeamName,
            String country,
            Long budget,
            Long stadiumCapacity) {

        footballTeamRepository.findByName(footballTeamName)
                .ifPresent(footballTeamEntity -> {
                    throw new BadRequestException(String.format("Football team with name \"%s\" already exists", footballTeamName));
                });

        if(budget < 50_000_000) {
            throw new BadRequestException("Your club's budget is not enough to join the tournament");
        }
        if (stadiumCapacity < 30000) {
            throw new BadRequestException("Your stadium capacity is not enough to join the tournament");
        }

        FootballTeamEntity footballTeam = footballTeamRepository.saveAndFlush(
                FootballTeamEntity.builder()
                        .name(footballTeamName)
                        .country(country)
                        .budget(budget)
                        .stadiumCapacity(stadiumCapacity)
                        .build());

        return footballTeamDtoFactory.makeDefault(footballTeam);
    }

    public FootballTeamDto updateFootballTeam(
            Long footballTeamId,
            String footballTeamName,
            String country,
            Long budget,
            Long stadiumCapacity) {

        if (footballTeamName.trim().isEmpty()) {
            throw new BadRequestException("Football team name can't be empty.");
        }

        if(budget < 50_000_000) {
            throw new BadRequestException("Your club's budget is not enough to join the tournament");
        }
        if (stadiumCapacity < 30000) {
            throw new BadRequestException("Your stadium capacity is not enough to join the tournament");
        }

        FootballTeamEntity footballTeam = footballTeamRepository
                .findById(footballTeamId)
                .orElseThrow(() -> new NotFoundException(String.format("Football team with \"%s\" not found", footballTeamId)));

        footballTeam.setName(footballTeamName);
        footballTeam.setCountry(country);
        footballTeam.setBudget(budget);
        footballTeam.setStadiumCapacity(stadiumCapacity);

        footballTeamRepository.saveAndFlush(footballTeam);

        return footballTeamDtoFactory.makeDefault(footballTeam);
    }

    public AckDto deleteFootballTeam( Long footballTeamId) {

        footballTeamRepository.deleteById(footballTeamId);

        return AckDto.builder()
                .answer(true)
                .build();
    }
}
