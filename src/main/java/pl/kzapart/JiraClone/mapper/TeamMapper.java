package pl.kzapart.JiraClone.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kzapart.JiraClone.dto.TeamDto;
import pl.kzapart.JiraClone.model.Task;
import pl.kzapart.JiraClone.model.Team;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "numberOfTasks", expression = "java(mapTasks(team.getTasks()))")
    TeamDto mapTeamToDto(Team team);
    default Integer mapTasks(List<Task> numberOfTasks){return numberOfTasks.size();}


    @InheritInverseConfiguration
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Team mapDtoToTeam(TeamDto teamDto);
}
