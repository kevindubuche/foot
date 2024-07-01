package com.foot.team_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entity class representing a Player.
 * <p>
 * This class uses JPA annotations to map the entity to a database table.
 * Lombok annotation is used to generate boilerplate code like getters, setters, and constructors.
 * </p>
 * <p>
 * It includes validation annotations to ensure the integrity of the data.
 * </p>
 *
 * @author Kevin
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25, message = "25 characters maximum for name")
    @NotNull(message = "name is required")
    @NotEmpty(message = "name can not be empty")
    private String name;

    @Positive(message = "number must be positive")
    private Integer no; //numéro du joueur; on pourrait mettre @Column(unique = true) pour s'assurer que 2 joueurs n'aient pas le meme numéro

    private int matchPlayed; //nombre de match joué

    @Size(max = 50, message = "50 characters maximum for position")
    @NotNull(message = "position is required")
    @NotEmpty(message = "position can not be empty")
    private String position; // dans la vraie vie on pourrait mettre une enum, mais ici ca passe
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    /**
     * Gets the team associated with the player.
     * <p>
     * If the team is not null, it sets the players of the team to null to avoid circular references.
     * </p>
     *
     * @return the team associated with the player
     */
    public Team getTeam() {
        if (team != null) {
            team.setPlayers(null);
        }
        return team;
    }

}
