package com.foot.team_service.service;

import com.foot.team_service.exception.PlayerNotFoundException;
import com.foot.team_service.model.Player;
import com.foot.team_service.model.Team;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.repository.TeamRepository;
import com.foot.team_service.service.PlayerService;
import com.foot.team_service.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private PlayerService playerService;

    private Player player;
    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Team A");

        player = new Player();
        player.setId(1L);
        player.setName("Player 1");
        player.setTeam(team);
    }

    @Test
    void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Player> page = new PageImpl<>(Arrays.asList(player));
        when(playerRepository.findAll(pageRequest)).thenReturn(page);

        Page<Player> result = playerService.findAll(0, 10, "name", "asc");
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(playerRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void testCreatePlayer() {
        when(teamService.findById(anyLong())).thenReturn(team);
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.create(player);
        assertNotNull(result);
        assertEquals("Player 1", result.getName());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testFindById_PlayerExists() {
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        Player result = playerService.findById(1L);
        assertNotNull(result);
        assertEquals("Player 1", result.getName());
        verify(playerRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFindById_PlayerNotFound() {
        when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class, () -> playerService.findById(1L));
        verify(playerRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdatePlayer() {
        Player updatedPlayer = new Player();
        updatedPlayer.setName("Updated Player");
        updatedPlayer.setNo(10);
        updatedPlayer.setMatchPlayed(20);
        updatedPlayer.setPosition("Forward");

//        when(teamService.findById(anyLong())).thenReturn(team);
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

        Player result = playerService.update(updatedPlayer, 1L);
        assertNotNull(result);
        assertEquals("Updated Player", result.getName());
        verify(playerRepository, times(1)).findById(anyLong());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testDeletePlayer_PlayerExists() {
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));
        doNothing().when(playerRepository).deleteById(anyLong());

        ResponseEntity<?> response = playerService.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(playerRepository, times(1)).findById(anyLong());
        verify(playerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeletePlayer_PlayerNotFound() {
        when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class, () -> playerService.delete(1L));
        verify(playerRepository, times(1)).findById(anyLong());
        verify(playerRepository, never()).deleteById(anyLong());
    }

    @Test
    void testFindByTeam() {
        List<Player> players = Arrays.asList(player);
        when(playerRepository.findByTeamId(anyLong())).thenReturn(players);

        List<Player> result = playerService.findByTeam(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(playerRepository, times(1)).findByTeamId(anyLong());
    }
}
