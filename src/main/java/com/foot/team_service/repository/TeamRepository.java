package com.foot.team_service.repository;

import com.foot.team_service.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository interface for managing {@link Team} entities.
 * <p>
 * This interface extends {@link JpaRepository} and {@link PagingAndSortingRepository}
 * to provide CRUD operations and pagination/sorting capabilities.
 * </p>
 *
 * @author Kevin
 */
public interface TeamRepository extends JpaRepository<Team, Long>, PagingAndSortingRepository<Team, Long> {
}