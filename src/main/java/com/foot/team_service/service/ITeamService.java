package com.foot.team_service.service;

import com.foot.team_service.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * Interface for Team service operations.
 * <p>
 * This interface defines methods for basic CRUD operations and other team-related queries.
 * Implementations should provide concrete logic for each method defined here.
 * </p>
 */
public interface ITeamService {

    /**
     * Retrieves a paginated list of teams sorted by the specified criteria.
     *
     * @param page      the page number to retrieve
     * @param size      the size of the page to retrieve
     * @param sortBy    the field to sort by
     * @param direction the direction to sort (asc/desc)
     * @return a paginated list of teams
     */
    Page<Team> findAll(int page, int size, String sortBy, String direction);

    /**
     * Creates a new team.
     *
     * @param team the team to create
     * @return the created team
     */
    Team create(Team team);

    /**
     * Finds a team by its ID.
     *
     * @param id the ID of the team
     * @return the team with the specified ID
     */
    Team findById(Long id);

    /**
     * Updates an existing team.
     *
     * @param newTeam the team details to update
     * @param id      the ID of the team to update
     * @return the updated team
     */
    Team update(Team newTeam, Long id);

    /**
     * Deletes a team by its ID.
     *
     * @param id the ID of the team to delete
     * @return a response entity with no content status
     */
    ResponseEntity<?> delete(Long id);

}
