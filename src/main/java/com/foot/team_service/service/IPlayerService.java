package com.foot.team_service.service;

import com.foot.team_service.dto.PlayerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface for Player service operations.
 * <p>
 * This interface defines methods for basic CRUD operations and other player-related queries.
 * Implementations should provide concrete logic for each method defined here.
 * </p>
 */
public interface IPlayerService {

    /**
     * Retrieves a paginated list of players.
     *
     * @param pageable the pagination and sorting information
     * @return a paginated list of players
     */
    Page<PlayerDTO> findAll(Pageable pageable);

    /**
     * Creates a new player.
     *
     * @param playerDTO the player data transfer object to create
     * @return the created player DTO
     */
    PlayerDTO create(PlayerDTO playerDTO);

    /**
     * Finds a player by its ID.
     *
     * @param id the ID of the player
     * @return the player with the specified ID
     */
    PlayerDTO findById(Long id);

    /**
     * Updates an existing player.
     *
     * @param playerDTO the player DTO containing the updated details
     * @param id        the ID of the player to update
     * @return the updated player DTO
     */
    PlayerDTO update(PlayerDTO playerDTO, Long id);

    /**
     * Deletes a player by its ID.
     *
     * @param id the ID of the player to delete
     * @return a response entity with no content status
     */
    ResponseEntity<?> delete(Long id);

    /**
     * Finds players belonging to a specific team.
     *
     * @param teamId the ID of the team to find players for
     * @return a list of players belonging to the team
     */
    List<PlayerDTO> findByTeam(Long teamId);

}
