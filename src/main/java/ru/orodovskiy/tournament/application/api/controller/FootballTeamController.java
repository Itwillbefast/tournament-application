package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.FootballTeamDto;
import ru.orodovskiy.tournament.application.api.mapper.FootballTeamMapper;
import ru.orodovskiy.tournament.application.api.service.FootballTeamService;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FootballTeamController {

    private final FootballTeamService footballTeamService;

    private final FootballTeamMapper footballTeamMapper;

    private static final String GET_FOOTBALL_TEAMS = "/football-teams";
    private static final String CREATE_FOOTBALL_TEAM = "/football-teams";
    private static final String UPDATE_FOOTBALL_TEAM = "/football-teams/{football_team_id}";
    private static final String DELETE_FOOTBALL_TEAM = "/football-teams/{football_team_id}";

    @GetMapping(GET_FOOTBALL_TEAMS)
    public List<FootballTeamDto> getFootballTeams (
            @RequestParam("prefix_name") Optional<String> prefixName) {

        List<FootballTeamEntity> footballTeams = footballTeamService.getFootballTeamsWithOrWithoutPrefix(prefixName);

        return footballTeams.stream().map(footballTeamMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping(CREATE_FOOTBALL_TEAM)
    public FootballTeamDto createFootballTeam(
            @RequestBody FootballTeamEntity footballTeam) {

        return footballTeamMapper.toDto(footballTeamService.createFootballTeam(footballTeam));
    }

    @PatchMapping(UPDATE_FOOTBALL_TEAM)
    public FootballTeamDto updateFootballTeam (@PathVariable(value = "football_team_id") Long footballTeamId,
                                               @RequestBody FootballTeamEntity footballTeam
    ) {

        return footballTeamMapper.toDto(footballTeamService.updateFootballTeam(footballTeamId, footballTeam));
    }

    @DeleteMapping(DELETE_FOOTBALL_TEAM)
    public AckDto deleteFootballTeam(@PathVariable("football_team_id") Long footballTeamId) {

        footballTeamService.deleteFootballTeam(footballTeamId);

        return AckDto.builder()
                .answer(true)
                .build();
    }
}
