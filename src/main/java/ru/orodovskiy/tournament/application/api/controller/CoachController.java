package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.CoachDto;
import ru.orodovskiy.tournament.application.api.service.CoachService;
import ru.orodovskiy.tournament.application.store.entity.CoachEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    private static final String GET_COACHES = "/football-teams/{football_team_id}/coaches";
    private static final String CREATE_COACH = "/football-teams/{football_team_id}/coaches";
    private static final String UPDATE_COACH = "/football-teams/{football_team_id}/coaches/{coach_id}";
    private static final String DELETE_COACH = "/coaches/{coach_id}";

    @GetMapping(GET_COACHES)
    public ResponseEntity<List<CoachDto>> getCoaches(
            @PathVariable("football_team_id") Long footballTeamId) {

        return ResponseEntity.ok(coachService.getCoaches(footballTeamId));
    }

    @PostMapping(CREATE_COACH)
    public ResponseEntity<CoachDto> createCoach(@PathVariable(value = "football_team_id") Long footballTeamId,
                                @RequestBody CoachEntity coach) {

        return new ResponseEntity<>(coachService.createCoach(footballTeamId, coach), HttpStatus.CREATED);
    }

    @DeleteMapping(DELETE_COACH)
    public ResponseEntity<AckDto> deleteCoach(@PathVariable("coach_id") Long coachId){

        return ResponseEntity.ok(coachService.deleteCoach(coachId));
    }
}
