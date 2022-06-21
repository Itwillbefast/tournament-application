package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.CoachDto;
import ru.orodovskiy.tournament.application.api.mapper.CoachMapper;
import ru.orodovskiy.tournament.application.api.service.CoachService;
import ru.orodovskiy.tournament.application.store.entity.CoachEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    private final CoachMapper coachMapper;

    private static final String GET_COACHES = "/football-teams/{football_team_id}/coaches";
    private static final String CREATE_COACH = "/football-teams/{football_team_id}/coaches";
    private static final String UPDATE_COACH = "/football-teams/{football_team_id}/coaches/{coach_id}";
    private static final String DELETE_COACH = "/coaches/{coach_id}";

    @GetMapping(GET_COACHES)
    public List<CoachDto> getCoaches(
            @PathVariable("football_team_id") Long footballTeamId) {

        return coachService.getCoaches(footballTeamId).stream().map(coachMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping(CREATE_COACH)
    public CoachDto createCoach(@PathVariable(value = "football_team_id") Long footballTeamId,
                                @RequestBody CoachEntity coach) {

        return coachMapper.toDto(coachService.createCoach(footballTeamId, coach));
    }

    @DeleteMapping(DELETE_COACH)
    public ResponseEntity<AckDto> deleteCoach(@PathVariable("coach_id") Long coachId){

        return ResponseEntity.ok(coachService.deleteCoach(coachId));
    }
}
