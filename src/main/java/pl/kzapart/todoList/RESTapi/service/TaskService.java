package pl.kzapart.todoList.RESTapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.todoList.RESTapi.dto.TaskRequest;
import pl.kzapart.todoList.RESTapi.dto.TaskResponse;
import pl.kzapart.todoList.RESTapi.dto.TeamDto;
import pl.kzapart.todoList.RESTapi.exceptions.SpringTodoException;
import pl.kzapart.todoList.RESTapi.mapper.TaskMapper;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.Team;
import pl.kzapart.todoList.RESTapi.model.User;
import pl.kzapart.todoList.RESTapi.repository.TaskRepository;
import pl.kzapart.todoList.RESTapi.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TaskService {

    private final TeamRepository teamRepository;
    private final AuthService authService;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

//    @Transactional
//    public TeamDto save(TeamDto teamDto)
//    {
//        //User current = authService.getCurrentUser();
//        Team save = teamRepository.save(teamMapper.mapDtoToTeam(teamDto));
//        teamDto.setTeamId(save.getTeamId());
//        //save.setUser(current);
//        return teamDto;
//    }
    @Transactional
    public Task save(TaskRequest taskRequest)
    {
        Team team = teamRepository.findByName(taskRequest.getTeamName()).orElseThrow(()-> new SpringTodoException("Cannot found team by name "+taskRequest.getTeamName()));
        User user = authService.getCurrentUser();
        Task save = taskRepository.save(taskMapper.map(taskRequest, team, user));
        save.setTeam(team);
        save.setUser(user);
        return save;
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalStateException(teamId.toString()));
        List<Task> posts = taskRepository.findAllByTeam(team);
        return posts.stream().map(taskMapper::mapToDto).collect(Collectors.toList());
    }

}
