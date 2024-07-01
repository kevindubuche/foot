package com.foot.team_service.service;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.exception.ResourceNotFoundException;
import com.foot.team_service.model.Player;
import com.foot.team_service.model.Team;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.repository.TeamRepository;
import com.foot.team_service.utils.mapper.TeamMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Team service implementation.
 *
 * This service provides methods for managing teams and their players, including
 * creating, updating, deleting, and retrieving teams.
 *
 * @autor Kevin
 */
@Service
public class TeamService implements ITeamService {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(TeamService.class);
    @Autowired
    private TeamRepository repository;
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Retrieves a paginated list of teams sorted by the specified criteria.
     *
     * @param pageable the pagination and sorting information
     * @return a paginated list of team DTOs
     */
    public Page<TeamDTO> findAll(Pageable pageable) {
        Page<Team> teamsPageable = repository.findAll(pageable);
        List<TeamDTO> teamDTOs = teamsPageable.getContent().stream()
                .map(TeamMapper.INSTANCE::teamToTeamDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(teamDTOs, pageable, teamsPageable.getTotalElements());

    }

    /**
     * Creates a new team and associates its players if provided.
     *
     * @param teamDTO the DTO of the team to create
     * @return the created team DTO
     */
    @Transactional
    public TeamDTO create(TeamDTO teamDTO) {
        Team team = TeamMapper.INSTANCE.teamDTOToTeam(teamDTO);
        Team teamSaved = repository.save(team);
        for (Player player : team.getPlayers()) {
            player.setTeam(teamSaved);
            playerRepository.save(player);
        }
        LOGGER.info("Team created: {}", team);
        return TeamMapper.INSTANCE.teamToTeamDTO(teamSaved);
    }

    /**
     * Finds a team by its ID.
     *
     * @param id the ID of the team to find
     * @return the team DTO with the specified ID
     * @throws ResourceNotFoundException if the team is not found
     */
    @Transactional
    public TeamDTO findById(Long id) {
        Team team = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found for this id :: " + id));
        return TeamMapper.INSTANCE.teamToTeamDTO(team);
    }

    /**
     * Updates an existing team.
     *
     * @param teamDTO the team DTO with updated details
     * @param id      the ID of the team to update
     * @return the updated team DTO
     * @throws ResourceNotFoundException if the team is not found
     */
    @Transactional
    public TeamDTO update(TeamDTO teamDTO, Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found for this id :: " + id));
        Team team = TeamMapper.INSTANCE.teamDTOToTeam(teamDTO);
        team.setId(id);
        Team teamUpdate = repository.save(team);
        for (Player player : team.getPlayers()) {
            player.setTeam(teamUpdate);
            playerRepository.save(player);
        }
        LOGGER.info("Team updated: {}", team);
        return TeamMapper.INSTANCE.teamToTeamDTO(teamUpdate);
    }

    /**
     * Deletes a team by its ID.
     *
     * @param id the ID of the team to delete
     * @return a response entity with no content status
     * @throws ResourceNotFoundException if the team is not found
     */
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found for this id :: " + id));
        repository.deleteById(id);
        LOGGER.info("Team {} deleted", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}