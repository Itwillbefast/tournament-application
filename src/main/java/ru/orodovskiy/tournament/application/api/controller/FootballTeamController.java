package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.FootballTeamDto;
import ru.orodovskiy.tournament.application.api.service.FootballTeamService;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FootballTeamController {

    private final FootballTeamService footballTeamService;

    private static final String GET_FOOTBALL_TEAMS = "/football-teams";
    private static final String CREATE_FOOTBALL_TEAM = "/football-teams";
    private static final String UPDATE_FOOTBALL_TEAM = "/football-teams/{football_team_id}";
    private static final String DELETE_FOOTBALL_TEAM = "/football-teams/{football_team_id}";

    @GetMapping(GET_FOOTBALL_TEAMS)
    public ResponseEntity<List<FootballTeamDto>> getFootballTeams (
            @RequestParam("prefix_name") Optional<String> prefixName) {

        return ResponseEntity.ok(footballTeamService.getFootballTeams(prefixName));
    }

    @PostMapping(CREATE_FOOTBALL_TEAM)
    public ResponseEntity<FootballTeamDto> createFootballTeam(
            @RequestBody FootballTeamEntity footballTeam) {

        return new ResponseEntity<>(footballTeamService.createFootballTeam(footballTeam), HttpStatus.CREATED);
    }

    @PatchMapping(UPDATE_FOOTBALL_TEAM)
    public ResponseEntity<FootballTeamDto> updateFootballTeam (@PathVariable(value = "football_team_id") Long footballTeamId,
                                               @RequestBody FootballTeamEntity footballTeam
    ) {

        return new ResponseEntity<>(footballTeamService.updateFootballTeam(footballTeamId, footballTeam), HttpStatus.CREATED);
    }

    @DeleteMapping(DELETE_FOOTBALL_TEAM)
    public ResponseEntity<AckDto> deleteFootballTeam(@PathVariable("football_team_id") Long footballTeamId) {

        return ResponseEntity.ok(footballTeamService.deleteFootballTeam(footballTeamId));
    }
}
