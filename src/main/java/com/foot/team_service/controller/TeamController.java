package com.foot.team_service.controller;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.model.Team;
import com.foot.team_service.service.TeamService;
import com.foot.team_service.utils.ValidationUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
     * @param page      the page number to retrieve
     * @param size      the size of the page to retrieve
     * @param sortBy    the field to sort by
     * @param direction the direction to sort (asc/desc)
     * @return a ResponseEntity containing a paginated list of TeamDTOs
     */
    @GetMapping
    ResponseEntity<Page<TeamDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Page<Team> teams = service.findAll(page, size, sortBy, direction);
        Page<TeamDTO> teamDTOs = teams.map(service::convertToDTO);
        return new ResponseEntity<>(teamDTOs, HttpStatus.OK);
    }

    /**
     * Creates a new team.
     *
     * @param team          the team to create
     * @param bindingResult the result of the validation binding
     * @return a ResponseEntity containing the created TeamDTO or validation errors
     */
    @PostMapping
    ResponseEntity<?> create(@Valid @RequestBody Team team, BindingResult bindingResult) {
        ResponseEntity<?> errors = ValidationUtil.validate(bindingResult);
        if (errors != null) return errors;

        Team teamCreated = service.create(team);
        TeamDTO teamDTO = service.convertToDTO(teamCreated);
        return new ResponseEntity<>(teamDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a team by its ID.
     *
     * @param id the ID of the team to retrieve
     * @return a ResponseEntity containing the TeamDTO with the specified ID
     */
    @GetMapping("/{id}")
    ResponseEntity<TeamDTO> findById(@PathVariable Long id) {
        Team team = service.findById(id);
        TeamDTO teamDTO = service.convertToDTO(team);
        return new ResponseEntity<>(teamDTO, HttpStatus.OK);
    }

    /**
     * Updates an existing team.
     *
     * @param newTeam       the updated team details
     * @param id            the ID of the team to update
     * @param bindingResult the result of the validation binding
     * @return a ResponseEntity containing the updated TeamDTO or validation errors
     */
    @PutMapping("/{id}")
    ResponseEntity<?> update(@Valid @RequestBody Team newTeam, @PathVariable Long id, BindingResult bindingResult) {
        ResponseEntity<?> errors = ValidationUtil.validate(bindingResult);
        if (errors != null) return errors;

        Team team = service.update(newTeam, id);
        TeamDTO teamDTO = service.convertToDTO(team);
        return new ResponseEntity<>(teamDTO, HttpStatus.OK);
    }

    /**
     * Deletes a team by its ID.
     *
     * @param id the ID of the team to delete
     * @return a ResponseEntity with no content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
