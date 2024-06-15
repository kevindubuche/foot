package com.foot.team_service.service;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.exception.TeamNotFoundException;
import com.foot.team_service.model.Player;
import com.foot.team_service.model.Team;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.repository.TeamRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team;
    private Player player;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Team A");
        team.setAcronym("TA");
        team.setBudget(10000);

        player = new Player();
        player.setId(1L);
        player.setName("Player 1");
        player.setTeam(team);
    }

    @Test
    void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Team> page = new PageImpl<>(Arrays.asList(team));
        when(teamRepository.findAll(pageRequest)).thenReturn(page);
        when(playerRepository.findByTeamId(anyLong())).thenReturn(Arrays.asList(player));

        Page<Team> result = teamService.findAll(0, 10, "name", "asc");
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getContent().get(0).getPlayers().size());
        verify(teamRepository, times(1)).findAll(pageRequest);
        verify(playerRepository, times(1)).findByTeamId(anyLong());
    }

    @Test
    void testCreateTeam() {
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        Team result = teamService.create(team);
        assertNotNull(result);
        assertEquals("Team A", result.getName());
        verify(teamRepository, times(1)).save(any(Team.class));


    }

    @Test
    void testFindById_TeamExists() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));

        Team result = teamService.findById(1L);
        assertNotNull(result);
        assertEquals("Team A", result.getName());
        verify(teamRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFindById_TeamNotFound() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> teamService.findById(1L));
        verify(teamRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateTeam() {
        Team updatedTeam = new Team();
        updatedTeam.setName("Updated Team");
        updatedTeam.setAcronym("UT");
        updatedTeam.setBudget(20000);

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(updatedTeam);

        Team result = teamService.update(updatedTeam, 1L);
        assertNotNull(result);
        assertEquals("Updated Team", result.getName());
        verify(teamRepository, times(1)).findById(anyLong());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void testDeleteTeam_TeamExists() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        doNothing().when(teamRepository).deleteById(anyLong());

        ResponseEntity<?> response = teamService.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(teamRepository, times(1)).findById(anyLong());
        verify(teamRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteTeam_TeamNotFound() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> teamService.delete(1L));
        verify(teamRepository, times(1)).findById(anyLong());
        verify(teamRepository, never()).deleteById(anyLong());
    }

    @Test
    void testConvertToDTO() {
        TeamDTO result = teamService.convertToDTO(team);
        assertNotNull(result);
        assertEquals(team.getId(), result.getId());
        assertEquals(team.getName(), result.getName());
        assertEquals(team.getAcronym(), result.getAcronym());
        assertEquals(team.getBudget(), result.getBudget());
    }
}
