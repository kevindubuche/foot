package com.foot.team_service.utils.mapper;

import com.foot.team_service.dto.PlayerDTO;
import com.foot.team_service.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(source = "team.id", target = "teamId")
    PlayerDTO playerToPlayerDTO(Player player);

    @Mapping(source = "teamId", target = "team.id")
    Player playerDTOToPlayer(PlayerDTO playerDTO);

    List<PlayerDTO> playersToPlayerDTOs(List<Player> players);

}
