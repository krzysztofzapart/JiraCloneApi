package pl.kzapart.todoList.RESTapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.todoList.RESTapi.dto.TeamDto;
import pl.kzapart.todoList.RESTapi.exceptions.SpringTodoException;
import pl.kzapart.todoList.RESTapi.mapper.TeamMapper;
import pl.kzapart.todoList.RESTapi.model.Team;
import pl.kzapart.todoList.RESTapi.model.User;
import pl.kzapart.todoList.RESTapi.repository.TeamRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final AuthService authService;

    @Transactional
    public TeamDto save(TeamDto teamDto)
    {
       User current = authService.getCurrentUser();
       Team save = teamRepository.save(teamMapper.mapDtoToTeam(teamDto));
       teamDto.setTeamId(save.getTeamId());
       save.setUser(current);
       return teamDto;
    }


    @Transactional(readOnly = true)
    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::mapTeamToDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public TeamDto getTeam(Long id)
    {
        Team team = teamRepository.findById(id).orElseThrow(()-> new SpringTodoException("Cannot found team by id "+id));
        return teamMapper.mapTeamToDto(team);
    }
}
