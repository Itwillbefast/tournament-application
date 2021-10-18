package ru.orodovskiy.tournament.application.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.orodovskiy.tournament.application.api.dto.AckDto;
import ru.orodovskiy.tournament.application.api.dto.CoachDto;
import ru.orodovskiy.tournament.application.api.service.CoachService;

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
    public List<CoachDto> getCoaches(
            @PathVariable("football_team_id") Long footballTeamId) {

        return coachService.getCoaches(footballTeamId);
    }

    @PostMapping(CREATE_COACH)
    public CoachDto createCoach(
            @PathVariable(value = "football_team_id") Long footballTeamId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname", defaultValue = "") String surname,
            @RequestParam(value = "age") Integer age,
            @RequestParam(value = "category") String category) {

        return coachService.createCoach(footballTeamId, name, surname, age, category);
    }

    @DeleteMapping(DELETE_COACH)
    public AckDto deleteCoach(@PathVariable("coach_id") Long coachId){

        return coachService.deleteCoach(coachId);
    }
}
