package ru.orodovskiy.tournament.application.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.orodovskiy.tournament.application.api.exception.BadRequestException;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;
import ru.orodovskiy.tournament.application.store.repository.FootballTeamRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FootballTeamServiceTest {

    private final Long ID = 1L;

    @Mock
    private FootballTeamRepository footballTeamRepository;

    @InjectMocks
    private FootballTeamService footballTeamService;

    @Test
    void getFootballTeams() {

        FootballTeamEntity team1 = new FootballTeamEntity(1L, "Chelsea","England", 5000000L, 100000L, null, null);
        FootballTeamEntity team2 = new FootballTeamEntity(2L, "Arsenal","England", 5000000L, 100000L, null, null);
        List<FootballTeamEntity> listTeams = new ArrayList<>();
        listTeams.add(team1);
        listTeams.add(team2);

        when(footballTeamRepository.streamAllByNameStartsWithIgnoreCase("Che")).thenReturn(listTeams.stream().filter(team -> team.getName().startsWith("Che")));
        List<FootballTeamEntity> fetchedTeams = footballTeamService.getFootballTeamsWithOrWithoutPrefix(Optional.of("Che"));

        assertThat(fetchedTeams.size()).isEqualTo(1);
        assertThat(fetchedTeams.contains(team1)).isTrue();
    }

    @Test
    void createFootballTeam() {
        FootballTeamEntity footballTeam = mock(FootballTeamEntity.class);


        when(footballTeam.getBudget()).thenReturn(300000000L);
        when(footballTeam.getStadiumCapacity()).thenReturn(100000L);


        when(footballTeamRepository.saveAndFlush(footballTeam)).thenReturn(footballTeam);
        FootballTeamEntity savedFootballTeam1 = footballTeamService.createFootballTeam(footballTeam);

        assertThat(savedFootballTeam1).isNotNull();
        verify(footballTeamRepository, times(1)).saveAndFlush(footballTeam);
    }

    @Test()
    void shouldThrowExceptionWhenCreateFootballTeam() {
        final FootballTeamEntity footballTeam = mock(FootballTeamEntity.class);

        when(footballTeamRepository.findByName(footballTeam.getName())).thenReturn(Optional.of(footballTeam));
        Exception exception = assertThrows(BadRequestException.class, () -> footballTeamService.createFootballTeam(footballTeam));

        assertEquals(String.format("Football team with name \"%s\" already exists", footballTeam.getName()), exception.getMessage());
        verify(footballTeamRepository, never()).save(any(FootballTeamEntity.class));
    }

    @Test
    void updateUser() {
        FootballTeamEntity footballTeam = new FootballTeamEntity(1L, "Arsenal","England", 50000000L, 100000L, null, null);

        when(footballTeamRepository.saveAndFlush(footballTeam)).thenReturn(footballTeam);
        when(footballTeamRepository.findById(ID)).thenReturn(Optional.of(footballTeam));

        final FootballTeamEntity expected = footballTeamService.updateFootballTeam(ID, footballTeam);

        assertThat(expected).isNotNull();
        verify(footballTeamRepository).saveAndFlush(any(FootballTeamEntity.class));
    }

    @Test
    void deleteFootballTeam() {

        FootballTeamEntity footballTeam = mock(FootballTeamEntity.class);

        when(footballTeamRepository.findById(ID)).thenReturn(Optional.of(footballTeam));
        footballTeamService.deleteFootballTeam(ID);

        assertNotEquals(footballTeamRepository.findById(ID).orElse(null).getId(), ID);
        verify(footballTeamRepository, times(1)).deleteById(ID);
    }
}