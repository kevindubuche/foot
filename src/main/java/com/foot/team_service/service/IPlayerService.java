package com.foot.team_service.service;

import com.foot.team_service.model.Player;
import org.springframework.data.domain.Page;
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
     * Retrieves a paginated list of players sorted by the specified criteria.
     *
     * @param page      the page number to retrieve
     * @param size      the size of the page to retrieve
     * @param sortBy    the field to sort by
     * @param direction the direction to sort (asc/desc)
     * @return a paginated list of players
     */
    Page<Player> findAll(int page, int size, String sortBy, String direction);

    /**
     * Creates a new player.
     *
     * @param player the player to create
     * @return the created player
     */
    Player create(Player player);

    /**
     * Finds a player by its ID.
     *
     * @param id the ID of the player
     * @return the player with the specified ID
     */
    Player findById(Long id);

    /**
     * Updates an existing player.
     *
     * @param newPlayer the player details to update
     * @param id        the ID of the player to update
     * @return the updated player
     */
    Player update(Player newPlayer, Long id);

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
    List<Player> findByTeam(Long teamId);

}
