package com.foot.team_service.service;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.model.Player;
import com.foot.team_service.model.Team;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.repository.TeamRepository;
import com.foot.team_service.utils.mapper.PlayerMapper;
import com.foot.team_service.utils.mapper.TeamMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Player fakePlayer() {
        Player player = new Player(
                1L,
                "Jhon Doe",
                11,
                34,
                "Défenseur",
                new Team(
                        1L,
                        "Equipe de Nice",
                        "OGC",
                        null,
                        2000
                )
        );

        return player;
    }

    @Test
    void testFindAll() {
        Pageable pageable = mock(Pageable.class);
        Player player = new Player();

        Page<Player> page = new PageImpl<>(Collections.singletonList(player));

        when(playerRepository.findAll(pageable)).thenReturn(page);

        Page<PlayerDTO> result = playerService.findAll(pageable);

        assertEquals(1, result.getContent().size());
        verify(playerRepository, times(1)).findAll(pageable);
    }

    @Test
    void testCreate() {
        Player player = fakePlayer();
        Team team = fakePlayer().getTeam();
        PlayerDTO playerDTO = PlayerMapper.INSTANCE.playerToPlayerDTO(player);

        when(playerRepository.save(any(Player.class))).thenReturn(player);
        when(teamService.findById(team.getId())).thenReturn(TeamMapper.INSTANCE.teamToTeamDTO(team));

        PlayerDTO result = playerService.create(playerDTO);

        assertEquals(playerDTO, result);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void testFindById() {
        Long playerId = 1L;
        PlayerDTO playerDTO = new PlayerDTO(playerId, "Player Name", 10, 0, "Forward", null, null, null);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(PlayerMapper.INSTANCE.playerDTOToPlayer(playerDTO)));

        PlayerDTO result = playerService.findById(playerId);

        assertEquals(playerDTO, result);
        verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    void testUpdate() {
        Long playerId = 1L;
        PlayerDTO playerDTO = new PlayerDTO(playerId, "Updated Name", 10, 0, "Forward", null, null, null);
        Team fakeTeam = new Team();

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(PlayerMapper.INSTANCE.playerDTOToPlayer(playerDTO)));
        when(playerRepository.save(any(Player.class))).thenReturn(PlayerMapper.INSTANCE.playerDTOToPlayer(playerDTO));
        when(teamService.findById(anyLong())).thenReturn(TeamMapper.INSTANCE.teamToTeamDTO(fakeTeam));

        PlayerDTO result = playerService.update(playerDTO, playerId);

        assertEquals(playerDTO, result);
        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testDelete() {
        Long playerId = 1L;

        PlayerDTO playerDTO = new PlayerDTO(playerId, "Updated Name", 10, 0, "Forward", null, null, null);
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(PlayerMapper.INSTANCE.playerDTOToPlayer(playerDTO)));
        doNothing().when(playerRepository).deleteById(playerId);

        ResponseEntity<?> result = playerService.delete(playerId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(playerRepository, times(1)).deleteById(playerId);
    }

    @Test
    void testFindByTeam() {
        Long teamId = 1L;
        Player player = new Player();
        List<Player> players = Collections.singletonList(player);

        when(playerRepository.findByTeamId(teamId)).thenReturn(players);
        PlayerDTO playerDTO = new PlayerDTO(null, "Player Name", 10, 0, "Forward", 1L, null, null);

        List<PlayerDTO> result = playerService.findByTeam(teamId);

        assertEquals(1, result.size());
        verify(playerRepository, times(1)).findByTeamId(teamId);
    }
}
