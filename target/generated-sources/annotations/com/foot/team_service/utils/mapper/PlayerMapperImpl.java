package com.foot.team_service.utils.mapper;

import com.foot.team_service.dto.PlayerDTO;
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
public class PlayerMapperImpl implements PlayerMapper {

    @Override
    public PlayerDTO playerToPlayerDTO(Player player) {
        if ( player == null ) {
            return null;
        }

        Long teamId = null;
        Long id = null;
        String name = null;
        Integer no = null;
        int matchPlayed = 0;
        String position = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        teamId = playerTeamId( player );
        id = player.getId();
        name = player.getName();
        no = player.getNo();
        matchPlayed = player.getMatchPlayed();
        position = player.getPosition();
        createdAt = player.getCreatedAt();
        updatedAt = player.getUpdatedAt();

        PlayerDTO playerDTO = new PlayerDTO( id, name, no, matchPlayed, position, teamId, createdAt, updatedAt );

        return playerDTO;
    }

    @Override
    public Player playerDTOToPlayer(PlayerDTO playerDTO) {
        if ( playerDTO == null ) {
            return null;
        }

        Player player = new Player();

        player.setTeam( playerDTOToTeam( playerDTO ) );
        player.setCreatedAt( playerDTO.createdAt() );
        player.setUpdatedAt( playerDTO.updatedAt() );
        player.setId( playerDTO.id() );
        player.setMatchPlayed( playerDTO.matchPlayed() );
        player.setName( playerDTO.name() );
        player.setNo( playerDTO.no() );
        player.setPosition( playerDTO.position() );

        return player;
    }

    @Override
    public List<PlayerDTO> playersToPlayerDTOs(List<Player> players) {
        if ( players == null ) {
            return null;
        }

        List<PlayerDTO> list = new ArrayList<PlayerDTO>( players.size() );
        for ( Player player : players ) {
            list.add( playerToPlayerDTO( player ) );
        }

        return list;
    }

    private Long playerTeamId(Player player) {
        if ( player == null ) {
            return null;
        }
        Team team = player.getTeam();
        if ( team == null ) {
            return null;
        }
        Long id = team.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Team playerDTOToTeam(PlayerDTO playerDTO) {
        if ( playerDTO == null ) {
            return null;
        }

        Team team = new Team();

        team.setId( playerDTO.teamId() );

        return team;
    }
}
