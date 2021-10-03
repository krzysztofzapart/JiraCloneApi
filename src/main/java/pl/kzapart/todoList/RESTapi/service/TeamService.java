package pl.kzapart.todoList.RESTapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.todoList.RESTapi.dto.TeamDto;
import pl.kzapart.todoList.RESTapi.model.Team;
import pl.kzapart.todoList.RESTapi.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public TeamDto save(TeamDto teamDto)
    {
       Team save = teamRepository.save(mapTeamToDto(teamDto));
       teamDto.setId(save.getTeamId());
       return teamDto;
    }

    private Team mapTeamToDto(TeamDto teamDto) {
        return Team.builder()
                .name(teamDto.getName())
                .description(teamDto.getDescription())
                .build();

    }

    @Transactional(readOnly = true)
    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    private TeamDto mapToDto(Team team) {
        return TeamDto.builder()
                .name(team.getName())
                .Id(team.getTeamId())
                .numberOfTasks(team.getTasks().size())
                .build();
    }
}
