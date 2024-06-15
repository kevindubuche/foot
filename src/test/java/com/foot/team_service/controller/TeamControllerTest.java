package com.foot.team_service.controller;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.model.Team;
import com.foot.team_service.service.TeamService;
import com.foot.team_service.utils.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    private Team team;
    private TeamDTO teamDTO;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Team 1");
        team.setAcronym("T1");
        team.setBudget(1000);

        teamDTO = new TeamDTO();
        teamDTO.setId(1L);
        teamDTO.setName("Team 1");
        teamDTO.setAcronym("T1");
        teamDTO.setBudget(1000);
    }

    @Test
    void testFindAll() {
        Page<Team> teamPage = new PageImpl<>(Arrays.asList(team));
        when(teamService.findAll(anyInt(), anyInt(), anyString(), anyString())).thenReturn(teamPage);
        when(teamService.convertToDTO(any(Team.class))).thenReturn(teamDTO);

        ResponseEntity<Page<TeamDTO>> response = teamController.findAll(0, 10, "name", "asc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        verify(teamService, times(1)).findAll(anyInt(), anyInt(), anyString(), anyString());
        verify(teamService, times(1)).convertToDTO(any(Team.class));
    }

    @Test
    void testCreateTeam() {
        when(teamService.create(any(Team.class))).thenReturn(team);
        when(teamService.convertToDTO(any(Team.class))).thenReturn(teamDTO);

        BindingResult bindingResult = new BeanPropertyBindingResult(team, "team");
        ResponseEntity<?> response = teamController.create(team, bindingResult);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(teamDTO, response.getBody());
        verify(teamService, times(1)).create(any(Team.class));
        verify(teamService, times(1)).convertToDTO(any(Team.class));
    }

    @Test
    void testCreateTeam_InvalidName() {
        team.setName(""); // Name cannot be empty

        BindingResult bindingResult = new BeanPropertyBindingResult(team, "team");
        bindingResult.addError(new FieldError("team", "name", "Name cannot be empty"));

        ResponseEntity<?> response = teamController.create(team, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(teamService, times(0)).create(any(Team.class));
    }

    @Test
    void testCreateTeam_InvalidAcronym() {
        team.setAcronym(""); // Acronym cannot be empty

        BindingResult bindingResult = new BeanPropertyBindingResult(team, "team");
        bindingResult.addError(new FieldError("team", "acronym", "Acronym cannot be empty"));

        ResponseEntity<?> response = teamController.create(team, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(teamService, times(0)).create(any(Team.class));
    }

    @Test
    void testCreateTeam_InvalidBudget() {
        team.setBudget(-100); // Budget must be positive

        BindingResult bindingResult = new BeanPropertyBindingResult(team, "team");
        bindingResult.addError(new FieldError("team", "budget", "Budget must be positive"));

        ResponseEntity<?> response = teamController.create(team, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(teamService, times(0)).create(any(Team.class));
    }

    @Test
    void testFindById() {
        when(teamService.findById(anyLong())).thenReturn(team);
        when(teamService.convertToDTO(any(Team.class))).thenReturn(teamDTO);

        ResponseEntity<TeamDTO> response = teamController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(teamDTO, response.getBody());
        verify(teamService, times(1)).findById(anyLong());
        verify(teamService, times(1)).convertToDTO(any(Team.class));
    }

    @Test
    void testUpdateTeam() {
        when(teamService.update(any(Team.class), anyLong())).thenReturn(team);
        when(teamService.convertToDTO(any(Team.class))).thenReturn(teamDTO);

        BindingResult bindingResult = new BeanPropertyBindingResult(team, "team");
        ResponseEntity<?> response = teamController.update(team, 1L, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(teamDTO, response.getBody());
        verify(teamService, times(1)).update(any(Team.class), anyLong());
        verify(teamService, times(1)).convertToDTO(any(Team.class));
    }

    @Test
    void testUpdateTeam_InvalidName() {
        team.setName(""); // Name cannot be empty

        BindingResult bindingResult = new BeanPropertyBindingResult(team, "team");
        bindingResult.addError(new FieldError("team", "name", "Name cannot be empty"));

        ResponseEntity<?> response = teamController.update(team, 1L, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(teamService, times(0)).update(any(Team.class), anyLong());
    }

    @Test
    void testUpdateTeam_InvalidAcronym() {
        team.setAcronym(""); // Acronym cannot be empty

        BindingResult bindingResult = new BeanPropertyBindingResult(team, "team");
        bindingResult.addError(new FieldError("team", "acronym", "Acronym cannot be empty"));

        ResponseEntity<?> response = teamController.update(team, 1L, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(teamService, times(0)).update(any(Team.class), anyLong());
    }

    @Test
    void testUpdateTeam_InvalidBudget() {
        team.setBudget(-100); // Budget must be positive

        BindingResult bindingResult = new BeanPropertyBindingResult(team, "team");
        bindingResult.addError(new FieldError("team", "budget", "Budget must be positive"));

        ResponseEntity<?> response = teamController.update(team, 1L, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(teamService, times(0)).update(any(Team.class), anyLong());
    }

    @Test
    void testDeleteTeam() {
        when(teamService.delete(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<?> response = teamController.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(teamService, times(1)).delete(anyLong());
    }
}
