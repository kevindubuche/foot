package com.foot.team_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * Entity class representing a Team.
 * <p>
 * This class uses JPA annotations to map the entity to a database table.
 * Lombok annotations are used to generate boilerplate code like getters, setters, and constructors.
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
public class Team extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25, message = "25 characters maximum for name")
    @NotNull(message = "name is required")
    @NotEmpty(message = "name can not be empty")
    private String name;

    @Size(max = 10, message = "10 characters maximum for acronym")
    @NotNull(message = "acronym is required")
    @NotEmpty(message = "acronym can not be empty")
    private String acronym;
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Player> players;

    @NotNull(message = "budget is required")
    private Integer budget; // on stock les centimes et on divisera le montant par 100 pour l'afficher pour le user

    /**
     * Ensure players list is initialized to an empty ArrayList if null.
     */
    public List<Player> getPlayers() {
        if (players == null) {
            players = new ArrayList<>();
        }
        return players;
    }

    /**
     * Pre-remove hook to disassociate players from the team before the team is removed.
     * <p>
     * This method is called automatically by JPA before the entity is removed.
     * </p>
     */
    @PreRemove
    private void preRemove() {
        for (Player player : this.players) {
            player.setTeam(null);
        }
    }
}
