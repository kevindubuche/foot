package com.foot.team_service.exception;

/**
 * Exception thrown when a team is not found.
 */
public class TeamNotFoundException extends RuntimeException {

    /**
     * Constructs a new TeamNotFoundException with the specified team ID.
     *
     * @param id the team ID
     */
    public TeamNotFoundException(Long id) {
        super("Could not find team " + id);
    }
}
