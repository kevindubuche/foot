package com.foot.team_service.service;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.exception.TeamNotFoundException;
import com.foot.team_service.model.Player;
import com.foot.team_service.model.Team;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Team service implementation.
 *
 * @author Kevin
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
     * @param page      the page number to retrieve
     * @param size      the size of the page to retrieve
     * @param sortBy    the field to sort by
     * @param direction the direction to sort (asc/desc)
     * @return a paginated list of team
     */
    public Page<Team> findAll(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Team> teamsPageable = repository.findAll(pageable);
        List<Team> teams = teamsPageable.getContent();
        teams.forEach(team -> {
            List<Player> players = playerRepository.findByTeamId(team.getId());
            team.setPlayers(players);
        });
        return teamsPageable;
    }

    /**
     * Creates a new team and associates its players if provided.
     *
     * @param team the team to create
     * @return the created team
     */
    @Transactional
    public Team create(Team team) {
        Team teamSaved = repository.save(team);
        LOGGER.info("Team added: {}", team);

        for (Player player : team.getPlayers()) {
            player.setTeam(teamSaved);
            playerRepository.save(player);
        }
        return teamSaved;
    }

    /**
     * Finds a team by its ID.
     *
     * @param id the ID of the team
     * @return the team with the specified ID
     * @throws TeamNotFoundException if the team is not found
     */
    @Transactional
    public Team findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    /**
     * Updates an existing team.
     *
     * @param newTeam the team details to update
     * @param id      the ID of the team to update
     * @return the updated team
     * @throws TeamNotFoundException if the team is not found
     */
    @Transactional
    public Team update(Team newTeam, Long id) {
        Team teamUpdate = repository.findById(id)
                .map(team -> {
                    team.setName(newTeam.getName());
                    team.setAcronym(newTeam.getAcronym());
                    team.setBudget(newTeam.getBudget());
                    LOGGER.info("Team updated: {}", team);
                    return repository.save(team);
                })
                .orElseThrow(() -> new TeamNotFoundException(id));
        for (Player player : newTeam.getPlayers()) {
            player.setTeam(teamUpdate);
            playerRepository.save(player);
        }
        return teamUpdate;
    }

    /**
     * Deletes a team by its ID.
     *
     * @param id the ID of the team to delete
     * @return a response entity with no content status
     * @throws TeamNotFoundException if the team is not found
     */
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<Team> team = repository.findById(id);
        if (!team.isPresent()) {
            throw new TeamNotFoundException(id);
        }
        repository.deleteById(id);
        LOGGER.info("Team deleted: {}", team.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * Converts a Team entity to a TeamDTO.
     *
     * @param team the Team entity to convert
     * @return the converted TeamDTO
     */
    public TeamDTO convertToDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setAcronym(team.getAcronym());
        teamDTO.setBudget(team.getBudget());
        teamDTO.setCreatedAt(team.getCreatedAt());
        teamDTO.setUpdatedAt(team.getUpdatedAt());
        teamDTO.setPlayers(team.getPlayers().stream()
                .map(player -> {
                    TeamDTO.PlayerDTO playerDTO = new TeamDTO.PlayerDTO();
                    playerDTO.setId(player.getId());
                    playerDTO.setName(player.getName());
                    playerDTO.setNo(player.getNo());
                    playerDTO.setMatchPlayed(player.getMatchPlayed());
                    playerDTO.setPosition(player.getPosition());
                    playerDTO.setCreatedAt(player.getCreatedAt());
                    playerDTO.setUpdatedAt(player.getUpdatedAt());
                    return playerDTO;
                })
                .collect(Collectors.toList()));
        return teamDTO;
    }
}