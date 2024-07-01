package com.foot.team_service.utils.mapper;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.model.Player;
import com.foot.team_service.model.Team;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-28T18:55:51+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class TeamMapperImpl implements TeamMapper {

    @Override
    public TeamDTO teamToTeamDTO(Team team) {
        if ( team == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String acronym = null;
        Integer budget = null;
        List<PlayerDTO> players = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = team.getId();
        name = team.getName();
        acronym = team.getAcronym();
        budget = team.getBudget();
        players = playerListToPlayerDTOList( team.getPlayers() );
        createdAt = team.getCreatedAt();
        updatedAt = team.getUpdatedAt();

        TeamDTO teamDTO = new TeamDTO( id, name, acronym, budget, players, createdAt, updatedAt );

        return teamDTO;
    }

    @Override
    public Team teamDTOToTeam(TeamDTO teamDTO) {
        if ( teamDTO == null ) {
            return null;
        }

        Team team = new Team();

        team.setCreatedAt( teamDTO.createdAt() );
        team.setUpdatedAt( teamDTO.updatedAt() );
        team.setAcronym( teamDTO.acronym() );
        team.setBudget( teamDTO.budget() );
        team.setId( teamDTO.id() );
        team.setName( teamDTO.name() );
        team.setPlayers( playerDTOListToPlayerList( teamDTO.players() ) );

        return team;
    }

    protected PlayerDTO playerToPlayerDTO(Player player) {
        if ( player == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Integer no = null;
        int matchPlayed = 0;
        String position = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = player.getId();
        name = player.getName();
        no = player.getNo();
        matchPlayed = player.getMatchPlayed();
        position = player.getPosition();
        createdAt = player.getCreatedAt();
        updatedAt = player.getUpdatedAt();

        Long teamId = null;

        PlayerDTO playerDTO = new PlayerDTO( id, name, no, matchPlayed, position, teamId, createdAt, updatedAt );

        return playerDTO;
    }

    protected List<PlayerDTO> playerListToPlayerDTOList(List<Player> list) {
        if ( list == null ) {
            return null;
        }

        List<PlayerDTO> list1 = new ArrayList<PlayerDTO>( list.size() );
        for ( Player player : list ) {
            list1.add( playerToPlayerDTO( player ) );
        }

        return list1;
    }

    protected Player playerDTOToPlayer(PlayerDTO playerDTO) {
        if ( playerDTO == null ) {
            return null;
        }

        Player player = new Player();

        player.setCreatedAt( playerDTO.createdAt() );
        player.setUpdatedAt( playerDTO.updatedAt() );
        player.setId( playerDTO.id() );
        player.setMatchPlayed( playerDTO.matchPlayed() );
        player.setName( playerDTO.name() );
        player.setNo( playerDTO.no() );
        player.setPosition( playerDTO.position() );

        return player;
    }

    protected List<Player> playerDTOListToPlayerList(List<PlayerDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Player> list1 = new ArrayList<Player>( list.size() );
        for ( PlayerDTO playerDTO : list ) {
            list1.add( playerDTOToPlayer( playerDTO ) );
        }

        return list1;
    }
}
