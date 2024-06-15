package com.foot.team_service.service;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.exception.PlayerNotFoundException;
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

/**
 * Player service implementation.
 *
 * @author Kevin
 */
@Service
public class PlayerService implements IPlayerService {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(TeamService.class);
    @Autowired
    private PlayerRepository repository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamService teamService;

    /**
     * Retrieves a paginated list of players sorted by the specified criteria.
     *
     * @param page      the page number to retrieve
     * @param size      the size of the page to retrieve
     * @param sortBy    the field to sort by
     * @param direction the direction to sort (asc/desc)
     * @return a paginated list of players
     */
    public Page<Player> findAll(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return repository.findAll(pageable);
    }

    /**
     * Creates a new player and associates it with a team if provided.
     *
     * @param player the player to create
     * @return the created player
     */
    @Transactional
    public Player create(Player player) {
        if (player.getTeam() != null) {
            teamService.findById(player.getTeam().getId());
        }
        LOGGER.info("Player added: {}", player);
        Player playerSaved = repository.save(player);
        return playerSaved;
    }

    /**
     * Finds a player by its ID.
     *
     * @param id the ID of the player
     * @return the player with the specified ID
     * @throws PlayerNotFoundException if the player is not found
     */
    @Transactional
    public Player findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    /**
     * Updates an existing player.
     *
     * @param newPlayer the player details to update
     * @param id        the ID of the player to update
     * @return the updated player
     * @throws PlayerNotFoundException if the player is not found
     */
    @Transactional
    public Player update(Player newPlayer, Long id) {
        Team team = null;
        if (newPlayer.getTeam() != null) {
            team = teamService.findById(newPlayer.getTeam().getId());
        }
        Team finalTeam = team;
        return repository.findById(id)
                .map(player -> {
                    player.setName(newPlayer.getName());
                    player.setNo(newPlayer.getNo());
                    player.setMatchPlayed(newPlayer.getMatchPlayed());
                    player.setPosition(newPlayer.getPosition());
                    player.setTeam(finalTeam);
                    LOGGER.info("Player updated: {}", player);
                    return repository.save(player);
                })
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    /**
     * Deletes a player by its ID.
     *
     * @param id the ID of the player to delete
     * @return a response entity with no content status
     * @throws PlayerNotFoundException if the player is not found
     */
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<Player> player = repository.findById(id);
        if (!player.isPresent()) {
            throw new PlayerNotFoundException(id);
        }
        repository.deleteById(id);
        LOGGER.info("Player deleted: {}", player.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Finds players belonging to a team.
     *
     * @param id the ID of the team
     * @return the players belonging to a team with the specified ID
     * @throws PlayerNotFoundException if the player is not found
     */
    public List<Player> findByTeam(Long teamId) {
        return repository.findByTeamId(teamId);
    }

    public PlayerDTO convertToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setNo(player.getNo());
        playerDTO.setMatchPlayed(player.getMatchPlayed());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setCreatedAt(player.getCreatedAt());
        playerDTO.setUpdatedAt(player.getUpdatedAt());
        playerDTO.setTeamId(player.getTeam() != null ? player.getTeam().getId() : null);
        return playerDTO;
    }
}
