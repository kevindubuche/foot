package com.foot.team_service.service;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.exception.ResourceNotFoundException;
import com.foot.team_service.utils.mapper.PlayerMapper;
import com.foot.team_service.model.Player;
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
 * Player service implementation.
 * Provides methods to manage player entities, including CRUD operations and team associations.
 * Utilizes PlayerRepository for database operations and PlayerMapper for entity-DTO transformations.
 * Handles resource not found exceptions and logs significant events.
 *
 * @auther Kevin
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
     * @param pageable the pagination and sorting information
     * @return a paginated list of players
     */
    public Page<PlayerDTO> findAll(Pageable pageable) {
        Page<Player> pageablePlayers = repository.findAll(pageable);

        List<PlayerDTO> playerDTOs = pageablePlayers.getContent().stream()
                .map(PlayerMapper.INSTANCE::playerToPlayerDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(playerDTOs, pageable, pageablePlayers.getTotalElements());
    }

    /**
     * Creates a new player and associates it with a team if provided.
     *
     * @param playerDTO the player data transfer object to create
     * @return the created player DTO
     */
    @Transactional
    public PlayerDTO create(PlayerDTO playerDTO) {
        Player player = PlayerMapper.INSTANCE.playerDTOToPlayer(playerDTO);
        player.setTeam(
                playerDTO.teamId() == null ? null :
                        TeamMapper.INSTANCE.teamDTOToTeam(teamService.findById(playerDTO.teamId()))
        );
        Player playerSaved = repository.save(player);
        LOGGER.info("Player created: {}", playerSaved);
        return PlayerMapper.INSTANCE.playerToPlayerDTO(playerSaved);
    }

    /**
     * Finds a player by its ID.
     *
     * @param id the ID of the player
     * @return the player DTO with the specified ID
     * @throws ResourceNotFoundException if the player is not found
     */
    @Transactional
    public PlayerDTO findById(Long id) {
        Player player = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found for this id :: " + id));
        return PlayerMapper.INSTANCE.playerToPlayerDTO(player);
    }

    /**
     * Updates an existing player.
     *
     * @param playerDTO the player DTO containing the updated details
     * @param id        the ID of the player to update
     * @return the updated player DTO
     * @throws ResourceNotFoundException if the player is not found
     */
    @Transactional
    public PlayerDTO update(PlayerDTO playerDTO, Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found for this id :: " + id));
        Player player = PlayerMapper.INSTANCE.playerDTOToPlayer(playerDTO);
        player.setTeam(
                playerDTO.teamId() == null ? null :
                        TeamMapper.INSTANCE.teamDTOToTeam(teamService.findById(playerDTO.teamId()))
        );
        player.setId(id);

        Player playerUpdated = repository.save(player);
        LOGGER.info("Player updated: {}", playerUpdated);
        return PlayerMapper.INSTANCE.playerToPlayerDTO(playerUpdated);
    }

    /**
     * Deletes a player by its ID.
     *
     * @param id the ID of the player to delete
     * @return a response entity with no content status
     * @throws ResourceNotFoundException if the player is not found
     */
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found for this id :: " + id));
        repository.deleteById(id);
        LOGGER.info("Player {} deleted", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Finds players belonging to a team.
     *
     * @param teamId the ID of the team
     * @return the list of player DTOs belonging to the team with the specified ID
     * @throws ResourceNotFoundException if the team is not found
     */
    public List<PlayerDTO> findByTeam(Long teamId) {
        List<Player> players = repository.findByTeamId(teamId);
        return PlayerMapper.INSTANCE.playersToPlayerDTOs(players);
    }
}
