package pl.kzapart.JiraClone.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kzapart.JiraClone.dto.TeamDto;
import pl.kzapart.JiraClone.mapper.TeamMapper;
import pl.kzapart.JiraClone.model.Team;
import pl.kzapart.JiraClone.model.User;
import pl.kzapart.JiraClone.repository.TeamRepository;



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

        Set<Team> teams = teamRepository.findByUsers(user);
        return teams.stream()
                .map(teamMapper::mapTeamToDto)
                .collect(Collectors.toSet());
    }
}
