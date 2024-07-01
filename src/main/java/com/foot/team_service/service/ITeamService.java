package com.foot.team_service.service;

import com.foot.team_service.dto.TeamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * Retrieves a paginated list of teams.
     *
     * @param pageable the pagination and sorting information
     * @return a paginated list of teams
     */
    Page<TeamDTO> findAll(Pageable pageable);

    /**
     * Creates a new team.
     *
     * @param teamDTO the team data transfer object to create
     * @return the created team DTO
     */
    TeamDTO create(TeamDTO teamDTO);

    /**
     * Finds a team by its ID.
     *
     * @param id the ID of the team
     * @return the team with the specified ID
     */
    TeamDTO findById(Long id);

    /**
     * Updates an existing team.
     *
     * @param newTeamDTO the team DTO containing the updated details
     * @param id the ID of the team to update
     * @return the updated team DTO
     */
    TeamDTO update(TeamDTO newTeamDTO, Long id);

    /**
     * Deletes a team by its ID.
     *
     * @param id the ID of the team to delete
     * @return a response entity with no content status
     */
    ResponseEntity<?> delete(Long id);

}
