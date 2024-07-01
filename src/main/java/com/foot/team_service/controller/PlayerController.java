package com.foot.team_service.controller;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.service.PlayerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class for handling REST endpoints related to Player operations.
 */
@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(PlayerController.class);
    @Autowired
    private PlayerRepository repository;
    @Autowired
    private PlayerService service;

    /**
     * Retrieves a paginated list of players sorted by specified criteria.
     *
     * @param pageable the pagination and sorting information
     * @return a ResponseEntity containing a paginated list of PlayerDTOs
     */
    @GetMapping
    ResponseEntity<Page<PlayerDTO>> getPlayers(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PlayerDTO> pageablePlayers = service.findAll(pageable);
        return new ResponseEntity<>(pageablePlayers, HttpStatus.OK);
    }

    /**
     * Creates a new player.
     *
     * @param playerDTO the player data transfer object to create
     * @return a ResponseEntity containing the created PlayerDTO
     */
    @PostMapping
    ResponseEntity<?> createPlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        PlayerDTO playerDTOCreated = service.create(playerDTO);
        return new ResponseEntity<>(playerDTOCreated, HttpStatus.CREATED);
    }

    /**
     * Retrieves a player by its ID.
     *
     * @param id the ID of the player to retrieve
     * @return a ResponseEntity containing the PlayerDTO with the specified ID
     */
    @GetMapping("/{id}")
    ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        PlayerDTO playerDTO = service.findById(id);
        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }

    /**
     * Updates an existing player.
     *
     * @param newPlayerDTO the updated player details
     * @param id           the ID of the player to update
     * @return a ResponseEntity containing the updated PlayerDTO
     */
    @PutMapping("/{id}")
    ResponseEntity<?> updatePlayer(@Valid @RequestBody PlayerDTO newPlayerDTO, @PathVariable Long id) {
        PlayerDTO playerDTO = service.update(newPlayerDTO, id);
        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }

    /**
     * Deletes a player by its ID.
     *
     * @param id the ID of the player to delete
     * @return a ResponseEntity with no content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        return service.delete(id);
    }

    /**
     * Retrieves a list of players belonging to a specific team.
     *
     * @param teamId the ID of the team
     * @return a ResponseEntity containing a list of PlayerDTOs belonging to the team
     */
    @GetMapping("/teams/{teamId}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeam(@PathVariable Long teamId) {
        List<PlayerDTO> playerDTOs = service.findByTeam(teamId);
        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

}
