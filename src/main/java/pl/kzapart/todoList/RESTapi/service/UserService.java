package pl.kzapart.todoList.RESTapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kzapart.todoList.RESTapi.dto.TeamDto;
import pl.kzapart.todoList.RESTapi.mapper.TeamMapper;
import pl.kzapart.todoList.RESTapi.model.Team;
import pl.kzapart.todoList.RESTapi.model.User;
import pl.kzapart.todoList.RESTapi.repository.TeamRepository;



import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final AuthService authService;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    public Set<TeamDto> getUsersTeams(){
        User user = authService.getCurrentUser();

        Set<Team> teams = teamRepository.findAllByUsers(user);
        return teams.stream()
                .map(teamMapper::mapTeamToDto)
                .collect(Collectors.toSet());
    }
}
