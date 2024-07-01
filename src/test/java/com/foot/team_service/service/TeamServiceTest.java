package com.foot.team_service.service;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.model.Player;
import com.foot.team_service.model.Team;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.repository.TeamRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Pageable pageable = mock(Pageable.class);
        Team team = new Team();
        Page<Team> page = new PageImpl<>(Collections.singletonList(team));

        when(teamRepository.findAll(pageable)).thenReturn(page);
        TeamDTO teamDTO = new TeamDTO(null, "Team Name", "TNAME", 1000, Collections.emptyList(), null, null);

        Page<TeamDTO> result = teamService.findAll(pageable);

        assertEquals(1, result.getContent().size());
        verify(teamRepository, times(1)).findAll(pageable);
    }

    @Test
    void testCreate() {
        TeamDTO teamDTO = new TeamDTO(null, "Team Name", "TNAME", 1000, Collections.emptyList(), null, null);
        Team team = TeamMapper.INSTANCE.teamDTOToTeam(teamDTO);
        Team createdTeam = new Team();
        createdTeam.setId(1L);
        Player player = new Player();

        when(playerRepository.save(any(Player.class))).thenReturn(player);
        when(teamRepository.save(any(Team.class))).thenReturn(TeamMapper.INSTANCE.teamDTOToTeam(teamDTO));

        TeamDTO result = teamService.create(teamDTO);

        assertEquals(teamDTO, result);
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    void testFindById() {
        Long teamId = 1L;
        Team team = new Team();
        TeamDTO teamDTO = new TeamDTO(teamId, "Team Name", "TNAME", 1000, Collections.emptyList(), null, null);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(TeamMapper.INSTANCE.teamDTOToTeam(teamDTO)));

        TeamDTO result = teamService.findById(teamId);

        assertEquals(teamDTO, result);
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    void testUpdate() {
        Long teamId = 1L;
        TeamDTO teamDTO = new TeamDTO(teamId, "Updated Name", "UTNAME", 2000, Collections.emptyList(), null, null);
        Team team = new Team();
        Player player = new Player();

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(TeamMapper.INSTANCE.teamDTOToTeam(teamDTO));
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        TeamDTO result = teamService.update(teamDTO, teamId);

        assertEquals(teamDTO, result);
        verify(teamRepository, times(1)).findById(teamId);
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void testDelete() {
        Long teamId = 1L;
        TeamDTO teamDTO = new TeamDTO(teamId, "Updated Name", "UTNAME", 2000, Collections.emptyList(), null, null);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(TeamMapper.INSTANCE.teamDTOToTeam(teamDTO)));
        doNothing().when(teamRepository).deleteById(teamId);

        ResponseEntity<?> result = teamService.delete(teamId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(teamRepository, times(1)).deleteById(teamId);
    }
}
