package com.foot.team_service.config;

import com.foot.team_service.model.Player;
import com.foot.team_service.model.Team;
import com.foot.team_service.repository.PlayerRepository;
import com.foot.team_service.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Configuration class for loading initial data into the database.
 * <p>
 * This class creates 7 teams, each containing between 11 and 18 players with randomly generated names and attributes.
 * </p>
 */
@Configuration
public class LoadDatabase {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private static final Random RANDOM = new Random();
    private static final String[] PLAYER_POSITIONS = {"Gardien", "Défenseur", "Milieu", "Attaquant"};
    private static final String[] TEAM_NAMES = {
            "Equipe Nice Pro", "Equipe Nice U20", "Equipe Nice U17",
            "Equipe Nice U15", "Equipe Nice U12", "Equipe Nice U10", "Equipe Nice U8"};
    private static final String[] TEAM_ACRONYMS = {
            "OGC-PRO", "OGC-U20", "OGC-U17", "OGC-U15", "OGC-U12", "OGC-U10", "OGC-U8"};

    private static final String[] FIRST_NAMES = {
            "Alex", "Max", "Chris", "Jordan", "Taylor", "Morgan", "Jamie", "Casey", "Robin", "Dana"};
    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            List<Team> teams = new ArrayList<>();
            List<Player> players = new ArrayList<>();

            for (int i = 0; i < 7; i++) {
                Team team = new Team();
                team.setName(TEAM_NAMES[i]);
                team.setAcronym(TEAM_ACRONYMS[i]);
                team.setBudget(RANDOM.nextInt(5000) + 1000);
                teams.add(team);
                teamRepository.save(team);

                int numberOfPlayers = RANDOM.nextInt(8) + 11; // Random number between 11 and 18
                for (int j = 0; j < numberOfPlayers; j++) {
                    Player player = new Player();
                    String playerName = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)] + " " +
                            LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
                    player.setName(playerName);
                    player.setNo(RANDOM.nextInt(99) + 1);
                    player.setMatchPlayed(RANDOM.nextInt(100));
                    player.setPosition(PLAYER_POSITIONS[RANDOM.nextInt(PLAYER_POSITIONS.length)]);
                    player.setTeam(team);
                    players.add(player);
                }
            }

            playerRepository.saveAll(players);
        };
    }
}
