package com.foot.team_service.controller;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.service.PlayerService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPlayers() {
        Pageable pageable = mock(Pageable.class);
        Page<PlayerDTO> page = new PageImpl<>(Collections.emptyList());

        when(playerService.findAll(pageable)).thenReturn(page);

        ResponseEntity<Page<PlayerDTO>> response = playerController.getPlayers(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());

        verify(playerService, times(1)).findAll(pageable);
    }

    @Test
    void testCreatePlayer() {
        PlayerDTO playerDTO = new PlayerDTO(null, "Player Name", 10, 0, "Forward", null, null, null);
        PlayerDTO createdPlayer = new PlayerDTO(1L, "Player Name", 10, 0, "Forward", null, null, null);

        when(playerService.create(playerDTO)).thenReturn(createdPlayer);

        ResponseEntity<?> response = playerController.createPlayer(playerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPlayer, response.getBody());

        verify(playerService, times(1)).create(playerDTO);
    }

    @Test
    void testGetPlayerById() {
        Long playerId = 1L;
        PlayerDTO playerDTO = new PlayerDTO(playerId, "Player Name", 10, 0, "Forward", null, null, null);

        when(playerService.findById(playerId)).thenReturn(playerDTO);

        ResponseEntity<PlayerDTO> response = playerController.getPlayerById(playerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playerDTO, response.getBody());

        verify(playerService, times(1)).findById(playerId);
    }

    @Test
    void testUpdatePlayer() {
        Long playerId = 1L;
        PlayerDTO playerDTO = new PlayerDTO(playerId, "Updated Name", 10, 0, "Forward", null, null, null);

        when(playerService.update(playerDTO, playerId)).thenReturn(playerDTO);

        ResponseEntity<?> response = playerController.updatePlayer(playerDTO, playerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playerDTO, response.getBody());

        verify(playerService, times(1)).update(playerDTO, playerId);
    }

    @Test
    void testDeletePlayer() {
        Long playerId = 1L;

        when(playerService.delete(playerId)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());

        ResponseEntity<?> response = playerController.deletePlayer(playerId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(playerService, times(1)).delete(playerId);
    }

    @Test
    void testGetPlayersByTeam() {
        Long teamId = 1L;
        PlayerDTO playerDTO = new PlayerDTO(1L, "Player Name", 10, 0, "Forward", teamId, null, null);
        when(playerService.findByTeam(teamId)).thenReturn(Collections.singletonList(playerDTO));

        ResponseEntity<List<PlayerDTO>> response = playerController.getPlayersByTeam(teamId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(playerDTO), response.getBody());

        verify(playerService, times(1)).findByTeam(teamId);
    }
}
