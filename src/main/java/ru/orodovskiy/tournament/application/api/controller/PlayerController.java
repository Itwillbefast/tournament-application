package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.PlayerDto;
import ru.orodovskiy.tournament.application.api.service.PlayerService;
import ru.orodovskiy.tournament.application.store.entity.PlayerEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    private static final String GET_PLAYERS = "/football-teams/{football_team_id}/players";
    private static final String GET_ALL_PLAYERS = "/players";
    private static final String CREATE_PLAYER = "/football-teams/{football_team_id}/players";
    private static final String UPDATE_PLAYER = "/football-teams/{football_team_id}/players/{player_id}";
    private static final String DELETE_PLAYER = "/players/{player_id}";

    @GetMapping(GET_ALL_PLAYERS)
    public ResponseEntity<List<PlayerDto>> getAllPlayers(@RequestParam("prefix_name_of_country") Optional<String> prefixNameOfCountry) {

        return ResponseEntity.ok(playerService.getAllPlayers(prefixNameOfCountry));
    }

    @GetMapping(GET_PLAYERS)
    public ResponseEntity<List<PlayerDto>> getPlayers(@PathVariable("football_team_id") Long footballTeamId) {

        return ResponseEntity.ok(playerService.getPlayers(footballTeamId));
    }

    @PostMapping(CREATE_PLAYER)
    public ResponseEntity<PlayerDto> createPlayer(@PathVariable("football_team_id") Long footballTeamId,
                                  @RequestBody PlayerEntity player) {

        return new ResponseEntity<>(playerService.createPlayer(footballTeamId, player), HttpStatus.CREATED);
    }

    @DeleteMapping(DELETE_PLAYER)
    public ResponseEntity<AckDto> deletePlayer(@PathVariable("player_id") Long playerId) {

        return ResponseEntity.ok(playerService.deletePlayer(playerId));
    }

}
