package com.foot.team_service.controller;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling REST endpoints related to Team operations.
 */
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {
    @Autowired
    private TeamService service;

    /**
     * Retrieves a paginated list of teams sorted by specified criteria.
     *
     * @param pageable the pagination and sorting information
     * @return a ResponseEntity containing a paginated list of TeamDTOs
     */
    @GetMapping
    ResponseEntity<Page<TeamDTO>> getTeams(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<TeamDTO> teamDTOs = service.findAll(pageable);
        return new ResponseEntity<>(teamDTOs, HttpStatus.OK);
    }

    /**
     * Creates a new team.
     *
     * @param teamDTO the team data transfer object to create
     * @return a ResponseEntity containing the created TeamDTO
     */
    @PostMapping
    ResponseEntity<?> createTeam(@Valid @RequestBody TeamDTO teamDTO) {
        TeamDTO teamCreated = service.create(teamDTO);
        return new ResponseEntity<>(teamCreated, HttpStatus.CREATED);
    }

    /**
     * Retrieves a team by its ID.
     *
     * @param id the ID of the team to retrieve
     * @return a ResponseEntity containing the TeamDTO with the specified ID
     */
    @GetMapping("/{id}")
    ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        TeamDTO teamDTO = service.findById(id);
        return new ResponseEntity<>(teamDTO, HttpStatus.OK);
    }

    /**
     * Updates an existing team.
     *
     * @param newTeamDTO the updated team details
     * @param id         the ID of the team to update
     * @return a ResponseEntity containing the updated TeamDTO
     */
    @PutMapping("/{id}")
    ResponseEntity<?> getTeamById(@Valid @RequestBody TeamDTO newTeamDTO, @PathVariable Long id) {
        TeamDTO teamDTO = service.update(newTeamDTO, id);
        return new ResponseEntity<>(teamDTO, HttpStatus.OK);
    }

    /**
     * Deletes a team by its ID.
     *
     * @param id the ID of the team to delete
     * @return a ResponseEntity with no content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        return service.delete(id);
    }
}
