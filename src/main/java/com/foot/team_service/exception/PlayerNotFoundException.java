package com.foot.team_service.exception;

/**
 * Exception thrown when a player is not found.
 */
public class PlayerNotFoundException extends RuntimeException {

    /**
     * Constructs a new PlayerNotFoundException with the specified player ID.
     *
     * @param id the player ID
     */
    public PlayerNotFoundException(Long id) {
        super("Could not find player " + id);
    }
}
