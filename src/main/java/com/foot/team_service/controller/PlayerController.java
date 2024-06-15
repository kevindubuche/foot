package com.foot.team_service.controller;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.model.Player;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.service.PlayerService;
import com.foot.team_service.utils.ValidationUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
     * @param page      the page number to retrieve
     * @param size      the size of the page to retrieve
     * @param sortBy    the field to sort by
     * @param direction the direction to sort (asc/desc)
     * @return a ResponseEntity containing a paginated list of PlayerDTOs
     */
    @GetMapping
    ResponseEntity<Page<PlayerDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Page<Player> players = service.findAll(page, size, sortBy, direction);
        Page<PlayerDTO> playerDTOs = players.map(service::convertToDTO);
        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

    /**
     * Creates a new player.
     *
     * @param player        the player to create
     * @param bindingResult the result of the validation binding
     * @return a ResponseEntity containing the created PlayerDTO or validation errors
     */
    @PostMapping
    ResponseEntity<?> create(@Valid @RequestBody Player player, BindingResult bindingResult) {
        ResponseEntity<?> errors = ValidationUtil.validate(bindingResult);
        if (errors != null) return errors;

        Player playerCreated = service.create(player);
        PlayerDTO playerDTO = service.convertToDTO(playerCreated);
        return new ResponseEntity<>(playerDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a player by its ID.
     *
     * @param id the ID of the player to retrieve
     * @return a ResponseEntity containing the PlayerDTO with the specified ID
     */
    @GetMapping("/{id}")
    ResponseEntity<PlayerDTO> findById(@PathVariable Long id) {
        Player player = service.findById(id);
        PlayerDTO playerDTO = service.convertToDTO(player);
        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }

    /**
     * Updates an existing player.
     *
     * @param newPlayer     the updated player details
     * @param id            the ID of the player to update
     * @param bindingResult the result of the validation binding
     * @return a ResponseEntity containing the updated PlayerDTO or validation errors
     */
    @PutMapping("/{id}")
    ResponseEntity<?> update(@Valid @RequestBody Player newPlayer, @PathVariable Long id, BindingResult bindingResult) {
        ResponseEntity<?> errors = ValidationUtil.validate(bindingResult);
        if (errors != null) return errors;

        Player player = service.update(newPlayer, id);
        PlayerDTO playerDTO = service.convertToDTO(player);
        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }

    /**
     * Deletes a player by its ID.
     *
     * @param id the ID of the player to delete
     * @return a ResponseEntity with no content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    /**
     * Retrieves a list of players belonging to a specific team.
     *
     * @param teamId the ID of the team
     * @return a ResponseEntity containing a list of PlayerDTOs belonging to the team
     */
    @GetMapping("/teams/{teamId}")
    public ResponseEntity<List<PlayerDTO>> findByTeam(@PathVariable Long teamId) {
        List<Player> players = service.findByTeam(teamId);
        List<PlayerDTO> playerDTOs = players.stream()
                .map(service::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

}
