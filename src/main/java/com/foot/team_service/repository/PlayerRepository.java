package com.foot.team_service.repository;

import com.foot.team_service.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Player} entities.
 * <p>
 * This interface extends {@link JpaRepository} and {@link PagingAndSortingRepository}
 * to provide CRUD operations and pagination/sorting capabilities.
 * </p>
 * <p>
 * It also declares a custom method to find players by their team ID.
 * </p>
 * @autor Kevin
 */
public interface PlayerRepository extends JpaRepository<Player, Long>, PagingAndSortingRepository<Player, Long> {
    /**
     * Finds a list of players by the team ID.
     *
     * @param teamId the ID of the team
     * @return a list of players associated with the given team ID
     */
    List<Player> findByTeamId(Long teamId);
}
