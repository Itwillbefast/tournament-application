package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.FootballTeamDto;
import ru.orodovskiy.tournament.application.api.service.FootballTeamService;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequiredArgsConstructor
public class FootballTeamController {

    private final FootballTeamService footballTeamService;

    private static final String GET_FOOTBALL_TEAMS = "/football-teams";
    private static final String CREATE_FOOTBALL_TEAM = "/football-teams";
    private static final String UPDATE_FOOTBALL_TEAM = "/football-teams/{football_team_id}";
    private static final String DELETE_FOOTBALL_TEAM = "/football-teams/{football_team_id}";

    @GetMapping(GET_FOOTBALL_TEAMS)
    public List<FootballTeamDto> getFootballTeams (
            @RequestParam("prefix_name") Optional<String> prefixName) {

        return footballTeamService.getFootballTeams(prefixName);
    }

    @PostMapping(CREATE_FOOTBALL_TEAM)
    public FootballTeamDto createFootballTeam(
            @RequestParam(value = "name") String footballTeamName,
            @RequestParam(value = "country") String country,
            @RequestParam(value = "budget") Long budget,
            @RequestParam(value = "stadium_capacity") Long stadiumCapacity) {

        return footballTeamService.createFootballTeam(footballTeamName, country, budget, stadiumCapacity);
    }

    @PatchMapping(UPDATE_FOOTBALL_TEAM)
    public FootballTeamDto updateFootballTeam (@PathVariable(value = "football_team_id") Long footballTeamId,
                                               @RequestParam(value = "name") String footballTeamName,
                                               @RequestParam(value = "country") String country,
                                               @RequestParam(value = "budget") Long budget,
                                               @RequestParam(value = "stadium_capacity") Long stadiumCapacity) {

        return footballTeamService.updateFootballTeam(footballTeamId, footballTeamName, country, budget, stadiumCapacity);
    }

    @DeleteMapping(DELETE_FOOTBALL_TEAM)
    public AckDto deleteFootballTeam(@PathVariable("football_team_id") Long footballTeamId) {

        return footballTeamService.deleteFootballTeam(footballTeamId);
    }
}
