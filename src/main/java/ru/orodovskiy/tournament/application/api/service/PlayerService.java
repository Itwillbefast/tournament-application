package ru.orodovskiy.tournament.application.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.exception.BadRequestException;
import ru.orodovskiy.tournament.application.api.exception.NotFoundException;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;
import ru.orodovskiy.tournament.application.store.entity.PlayerEntity;
import ru.orodovskiy.tournament.application.store.repository.FootballTeamRepository;
import ru.orodovskiy.tournament.application.store.repository.PlayerRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final FootballTeamRepository footballTeamRepository;

    @Transactional
    public List<PlayerEntity> getAllPlayers(Optional<String> prefixNameOfCountry) {

        Stream<PlayerEntity> playersStream = prefixNameOfCountry
                .map(playerRepository::streamAllByCountryStartsWithIgnoreCase)
                .orElseGet(playerRepository::streamAllBy);

        return playersStream.collect(Collectors.toList());
    }

    @Transactional
    public List<PlayerEntity> getPlayers(Long footballTeamId) {

        FootballTeamEntity footballTeam = footballTeamRepository
                .findById(footballTeamId)
                .orElseThrow(() -> new NotFoundException(String.format("Football team with id \"%d\" not found", footballTeamId)));

        return footballTeam.getPlayers();
    }

    @Transactional
    public PlayerEntity createPlayer(Long footballTeamId, PlayerEntity player) {

        FootballTeamEntity footballTeam = footballTeamRepository
                .findById(footballTeamId)
                .orElseThrow(() -> new NotFoundException(String.format("Football team with id \"%d\" not found", footballTeamId)));

        if(footballTeam.getPlayers().size() >= 23) {
            throw new BadRequestException("Amount a players in one team can't be more than 23");
        }

        player.setFootballTeam(footballTeam);
        playerRepository.saveAndFlush(player);

        return player;
    }

    @Transactional
    public AckDto deletePlayer(Long playerId) {

        playerRepository.deleteById(playerId);

        return AckDto.builder().answer(true).build();
    }
}
