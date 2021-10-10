package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.PlayerDto;
import ru.orodovskiy.tournament.application.api.service.PlayerService;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    private static final String GET_PLAYERS = "/football-teams/{football_team_id}/players";
    private static final String GET_ALL_PLAYERS = "/players";
    private static final String CREATE_PLAYER = "/football-teams/{football_team_id}/players";
    private static final String UPDATE_PLAYER = "/football-teams/{football_team_id}/players/{player_id}";
    private static final String DELETE_PLAYER = "/players/{player_id}";

    @GetMapping(GET_ALL_PLAYERS)
    public List<PlayerDto> getAllPlayers(@RequestParam("prefix_name_of_country") Optional<String> prefixNameOfCountry) {

        return playerService.getAllPlayers(prefixNameOfCountry);
    }

    @GetMapping(GET_PLAYERS)
    public List<PlayerDto> getPlayers(@PathVariable("football_team_id") Long footballTeamId) {

        return playerService.getPlayers(footballTeamId);
    }

    @PostMapping(CREATE_PLAYER)
    public PlayerDto createPlayer(
            @PathVariable("football_team_id") Long footballTeamId,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "country") String country,
            @RequestParam(value = "age") Integer age) {

        return playerService.createPlayer(footballTeamId, name, surname, country, age);
    }

    @DeleteMapping(DELETE_PLAYER)
    public AckDto deletePlayer(@PathVariable("player_id") Long playerId) {

        return playerService.deletePlayer(playerId);
    }

}
