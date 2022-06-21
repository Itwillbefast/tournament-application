package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.PlayerDto;
import ru.orodovskiy.tournament.application.api.mapper.PlayerMapper;
import ru.orodovskiy.tournament.application.api.service.PlayerService;
import ru.orodovskiy.tournament.application.store.entity.PlayerEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    private final PlayerMapper playerMapper;

    private static final String GET_PLAYERS = "/football-teams/{football_team_id}/players";
    private static final String GET_ALL_PLAYERS = "/players";
    private static final String CREATE_PLAYER = "/football-teams/{football_team_id}/players";
    private static final String UPDATE_PLAYER = "/football-teams/{football_team_id}/players/{player_id}";
    private static final String DELETE_PLAYER = "/players/{player_id}";

    @GetMapping(GET_ALL_PLAYERS)
    public List<PlayerDto> getAllPlayers(@RequestParam("prefix_name_of_country") Optional<String> prefixNameOfCountry) {

        List<PlayerEntity> playersList = playerService.getAllPlayers(prefixNameOfCountry);

        return playersList.stream().map(playerMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(GET_PLAYERS)
    public List<PlayerDto> getPlayers(@PathVariable("football_team_id") Long footballTeamId) {

        return playerService.getPlayers(footballTeamId).stream().map(playerMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping(CREATE_PLAYER)
    public PlayerDto createPlayer(@PathVariable("football_team_id") Long footballTeamId,
                                  @RequestBody PlayerEntity player) {

        return playerMapper.toDto(playerService.createPlayer(footballTeamId, player));
    }

    @DeleteMapping(DELETE_PLAYER)
    public ResponseEntity<AckDto> deletePlayer(@PathVariable("player_id") Long playerId) {

        return ResponseEntity.ok(playerService.deletePlayer(playerId));
    }
}
