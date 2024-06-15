package com.foot.team_service.controller;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.model.Player;
import com.foot.team_service.service.PlayerService;
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
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    private Player player;
    private PlayerDTO playerDTO;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setId(1L);
        player.setName("Player 1");
        player.setPosition("Forward");
        player.setMatchPlayed(10);
        player.setNo(9);

        playerDTO = new PlayerDTO();
        playerDTO.setId(1L);
        playerDTO.setName("Player 1");
        playerDTO.setPosition("Forward");
        playerDTO.setMatchPlayed(10);
        playerDTO.setNo(9);
    }

    @Test
    void testFindAll() {
        Page<Player> playerPage = new PageImpl<>(Arrays.asList(player));
        when(playerService.findAll(anyInt(), anyInt(), anyString(), anyString())).thenReturn(playerPage);
        when(playerService.convertToDTO(any(Player.class))).thenReturn(playerDTO);

        ResponseEntity<Page<PlayerDTO>> response = playerController.findAll(0, 10, "name", "asc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        verify(playerService, times(1)).findAll(anyInt(), anyInt(), anyString(), anyString());
        verify(playerService, times(1)).convertToDTO(any(Player.class));
    }

    @Test
    void testCreatePlayer() {
        when(playerService.create(any(Player.class))).thenReturn(player);
        when(playerService.convertToDTO(any(Player.class))).thenReturn(playerDTO);

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        ResponseEntity<?> response = playerController.create(player, bindingResult);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(playerDTO, response.getBody());
        verify(playerService, times(1)).create(any(Player.class));
        verify(playerService, times(1)).convertToDTO(any(Player.class));
    }

    @Test
    void testCreatePlayer_InvalidName() {
        player.setName(""); // Name cannot be empty

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        bindingResult.addError(new FieldError("player", "name", "Name cannot be empty"));

        ResponseEntity<?> response = playerController.create(player, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(playerService, times(0)).create(any(Player.class));
    }

    @Test
    void testCreatePlayer_InvalidPosition() {
        player.setPosition(""); // Position cannot be empty

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        bindingResult.addError(new FieldError("player", "position", "Position cannot be empty"));

        ResponseEntity<?> response = playerController.create(player, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(playerService, times(0)).create(any(Player.class));
    }

    @Test
    void testCreatePlayer_InvalidMatchPlayed() {
        player.setMatchPlayed(-1); // matchPlayed must be positive

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        bindingResult.addError(new FieldError("player", "matchPlayed", "matchPlayed must be positive"));

        ResponseEntity<?> response = playerController.create(player, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(playerService, times(0)).create(any(Player.class));
    }

    @Test
    void testCreatePlayer_InvalidNo() {
        player.setNo(-1); // no must be positive

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        bindingResult.addError(new FieldError("player", "no", "no must be positive"));

        ResponseEntity<?> response = playerController.create(player, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(playerService, times(0)).create(any(Player.class));
    }

    @Test
    void testFindById() {
        when(playerService.findById(anyLong())).thenReturn(player);
        when(playerService.convertToDTO(any(Player.class))).thenReturn(playerDTO);

        ResponseEntity<PlayerDTO> response = playerController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(playerDTO, response.getBody());
        verify(playerService, times(1)).findById(anyLong());
        verify(playerService, times(1)).convertToDTO(any(Player.class));
    }

    @Test
    void testUpdatePlayer() {
        when(playerService.update(any(Player.class), anyLong())).thenReturn(player);
        when(playerService.convertToDTO(any(Player.class))).thenReturn(playerDTO);

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        ResponseEntity<?> response = playerController.update(player, 1L, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(playerDTO, response.getBody());
        verify(playerService, times(1)).update(any(Player.class), anyLong());
        verify(playerService, times(1)).convertToDTO(any(Player.class));
    }

    @Test
    void testUpdatePlayer_InvalidName() {
        player.setName(""); // Name cannot be empty

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        bindingResult.addError(new FieldError("player", "name", "Name cannot be empty"));

        ResponseEntity<?> response = playerController.update(player, 1L, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(playerService, times(0)).update(any(Player.class), anyLong());
    }

    @Test
    void testUpdatePlayer_InvalidPosition() {
        player.setPosition(""); // Position cannot be empty

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        bindingResult.addError(new FieldError("player", "position", "Position cannot be empty"));

        ResponseEntity<?> response = playerController.update(player, 1L, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(playerService, times(0)).update(any(Player.class), anyLong());
    }

    @Test
    void testUpdatePlayer_InvalidMatchPlayed() {
        player.setMatchPlayed(-1); // matchPlayed must be positive

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        bindingResult.addError(new FieldError("player", "matchPlayed", "matchPlayed must be positive"));

        ResponseEntity<?> response = playerController.update(player, 1L, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(playerService, times(0)).update(any(Player.class), anyLong());
    }

    @Test
    void testUpdatePlayer_InvalidNo() {
        player.setNo(-1); // no must be positive

        BindingResult bindingResult = new BeanPropertyBindingResult(player, "player");
        bindingResult.addError(new FieldError("player", "no", "no must be positive"));

        ResponseEntity<?> response = playerController.update(player, 1L, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(playerService, times(0)).update(any(Player.class), anyLong());
    }

    @Test
    void testDeletePlayer() {
        when(playerService.delete(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<?> response = playerController.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(playerService, times(1)).delete(anyLong());
    }

    @Test
    void testFindByTeam() {
        List<Player> players = Arrays.asList(player);
        when(playerService.findByTeam(anyLong())).thenReturn(players);
        when(playerService.convertToDTO(any(Player.class))).thenReturn(playerDTO);

        ResponseEntity<List<PlayerDTO>> response = playerController.findByTeam(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(playerService, times(1)).findByTeam(anyLong());
        verify(playerService, times(1)).convertToDTO(any(Player.class));
    }
}
