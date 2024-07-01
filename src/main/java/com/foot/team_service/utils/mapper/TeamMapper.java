package com.foot.team_service.utils.mapper;

import com.foot.team_service.dto.TeamDTO;
import com.foot.team_service.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    TeamDTO teamToTeamDTO(Team team);

    Team teamDTOToTeam(TeamDTO teamDTO);

}