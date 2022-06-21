package ru.orodovskiy.tournament.application.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.orodovskiy.tournament.application.store.entity.FootballTeamEntity;
import ru.orodovskiy.tournament.application.store.entity.PlayerEntity;
import ru.orodovskiy.tournament.application.store.repository.FootballTeamRepository;
import ru.orodovskiy.tournament.application.store.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    private final Long ID = 1L;

    @Mock
    private FootballTeamRepository footballTeamRepository;

    @Mock FootballTeamService footballTeamService;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void getAllPlayers() {

        PlayerEntity player = new PlayerEntity(ID, "Alexey", "Zuev", "France", 22, mock(FootballTeamEntity.class));

        when(playerRepository.streamAllByCountryStartsWithIgnoreCase(anyString())).thenReturn(Stream.of(player));

        List<PlayerEntity> allPlayers = playerService.getAllPlayers(Optional.of("Fra"));

        assertThat(allPlayers.size()).isGreaterThanOrEqualTo(1);
        assertThat(allPlayers.stream().findFirst().orElse(null).getCountry()).isEqualTo(player.getCountry());
    }

    @Test
    void getPlayers() {
        FootballTeamEntity footballTeam = mock(FootballTeamEntity.class);

        when(footballTeamRepository.findById(ID)).thenReturn(Optional.of(footballTeam));

        List<PlayerEntity> players = playerService.getPlayers(ID);

        assertThat(footballTeam.getPlayers()).isEqualTo(players);

        assertThat(players.size()).isGreaterThan(-1);
    }

    @Test
    void createPlayer() {
    }

    @Test
    void deletePlayer() {
    }
}