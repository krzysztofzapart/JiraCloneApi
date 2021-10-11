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
import pl.kzapart.todoList.RESTapi.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public TeamDto save(TeamDto teamDto)
    {
       User current = authService.getCurrentUser();
       Team save = teamRepository.save(teamMapper.mapDtoToTeam(teamDto));
       User newUser = userRepository.findById(2L).orElseThrow(() -> new SpringTodoException("No such user found"));
       teamDto.setTeamId(save.getTeamId());
       Set<User> newy = new HashSet<>();
       newy.add(current);
       newy.add(newUser);
       save.setUsers(newy);
       return teamDto;
    }

    @Transactional
    public void addUserToTeam(Long teamId, Long userId)
    {
        User newUser = userRepository.findById(userId).orElseThrow(() -> new SpringTodoException("No such user found"));
        Team upTeam = teamRepository.findById(teamId).orElseThrow(() -> new SpringTodoException("No such team found"));

        upTeam.getUsers().add(newUser);

        teamRepository.save(upTeam);

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

    @Transactional
    public void editTeam(TeamDto teamDto)
    {
        Team team = teamRepository.findById(teamDto.getTeamId()).orElseThrow(() -> new SpringTodoException("No such team found"));
        team.setDescription(teamDto.getDescription());
        team.setName(teamDto.getName());
    }

    @Transactional
    public void deleteTeam(Long teamId)
    {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new SpringTodoException("No such team found"));
        teamRepository.delete(team);
    }
    @Transactional
    public void deleteUserFromTeam(Long teamId, Long userId)
    {
        User newUser = userRepository.findById(userId).orElseThrow(() -> new SpringTodoException("No such user found"));
        Team upTeam = teamRepository.findById(teamId).orElseThrow(() -> new SpringTodoException("No such team found"));

        upTeam.getUsers().remove(newUser);

        teamRepository.save(upTeam);

    }
}
