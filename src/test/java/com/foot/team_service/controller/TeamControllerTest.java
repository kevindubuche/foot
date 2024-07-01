package com.foot.team_service.controller;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTeams() {
        Pageable pageable = mock(Pageable.class);
        Page<TeamDTO> page = new PageImpl<>(Collections.emptyList());

        when(teamService.findAll(pageable)).thenReturn(page);

        ResponseEntity<Page<TeamDTO>> response = teamController.getTeams(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());

        verify(teamService, times(1)).findAll(pageable);
    }

    @Test
    void testCreateTeam() {
        TeamDTO teamDTO = new TeamDTO(null, "Team Name", "TNAME", 1000, Collections.emptyList(), null, null);
        TeamDTO createdTeam = new TeamDTO(1L, "Team Name", "TNAME", 1000, Collections.emptyList(), null, null);

        when(teamService.create(teamDTO)).thenReturn(createdTeam);

        ResponseEntity<?> response = teamController.createTeam(teamDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTeam, response.getBody());

        verify(teamService, times(1)).create(teamDTO);
    }

    @Test
    void testGetTeamById() {
        Long teamId = 1L;
        TeamDTO teamDTO = new TeamDTO(teamId, "Team Name", "TNAME", 1000, Collections.emptyList(), null, null);

        when(teamService.findById(teamId)).thenReturn(teamDTO);

        ResponseEntity<TeamDTO> response = teamController.getTeamById(teamId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teamDTO, response.getBody());

        verify(teamService, times(1)).findById(teamId);
    }

    @Test
    void testUpdateTeam() {
        Long teamId = 1L;
        TeamDTO teamDTO = new TeamDTO(teamId, "Updated Name", "UTNAME", 2000, Collections.emptyList(), null, null);

        when(teamService.update(teamDTO, teamId)).thenReturn(teamDTO);

        ResponseEntity<?> response = teamController.getTeamById(teamDTO, teamId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teamDTO, response.getBody());

        verify(teamService, times(1)).update(teamDTO, teamId);
    }

    @Test
    void testDeleteTeam() {
        Long teamId = 1L;

        when(teamService.delete(teamId)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());

        ResponseEntity<?> response = teamController.deleteTeam(teamId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(teamService, times(1)).delete(teamId);
    }
}
