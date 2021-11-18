package pl.kzapart.todoList.RESTapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.todoList.RESTapi.dto.TaskRequest;
import pl.kzapart.todoList.RESTapi.dto.TaskResponse;
import pl.kzapart.todoList.RESTapi.exceptions.SpringTodoException;
import pl.kzapart.todoList.RESTapi.mapper.TaskMapper;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.Team;
import pl.kzapart.todoList.RESTapi.model.User;
import pl.kzapart.todoList.RESTapi.model.dict.TaskStatus;
import pl.kzapart.todoList.RESTapi.repository.TaskRepository;
import pl.kzapart.todoList.RESTapi.repository.TeamRepository;
import pl.kzapart.todoList.RESTapi.repository.UserRepository;

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
    private final UserRepository userRepository;

    @Transactional
    public Task save(TaskRequest taskRequest)
    {
        Team team = teamRepository.findByName(taskRequest.getTeamName()).orElseThrow(()-> new SpringTodoException("Cannot found team by name "+taskRequest.getTeamName()));
        User user = userRepository.findByUsername(taskRequest.getUsername()).orElseThrow(() -> new SpringTodoException("Cannot found user by name "+taskRequest.getUsername()));
        Task save = taskRepository.save(taskMapper.map(taskRequest, team, user));
        User currentUser = authService.getCurrentUser();

        save.setTeam(team);
        save.setUserName(taskRequest.getUsername());
        save.setCreatedBy(currentUser.getUsername());
        return save;
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalStateException(teamId.toString()));
        List<Task> tasks = taskRepository.findAllByTeam(team);
        return tasks.stream().map(taskMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByUser(java.lang.String name)
    {
        User user = userRepository.findByUsername(name).orElseThrow(()-> new SpringTodoException("No such user as "+name));
        List<Task> tasks = taskRepository.findAllByUserName(user.getUsername());

        return tasks.stream()
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getMyTasks()
    {
        User user = authService.getCurrentUser();
        List<Task> tasks = taskRepository.findAllByUserName(user.getUsername());
        return tasks
                .stream()
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void editTask(TaskRequest taskRequest)
    {
        if (checkIfUserIsOwner(taskRequest.getTaskId()))
        {
            Task editedTask = taskRepository.findById(taskRequest.getTaskId()).orElseThrow(() -> new SpringTodoException("No such task found"));
            taskRepository.save(editedTask);
            editedTask.setTaskName(taskRequest.getTaskName());
            editedTask.setDescription(taskRequest.getDescription());
            editedTask.setUrl(taskRequest.getUrl());
        }
        else
            throw new SpringTodoException("User is not the owner of the task");


    }
    @Transactional
    public void editTaskStatus(TaskStatus taskStatus, Long taskId)
    {
        if (checkIfUserIsOwner(taskId))
        {
            Task editedTask = taskRepository.findById(taskId).orElseThrow(() -> new SpringTodoException("No such task found"));
            taskRepository.save(editedTask);
            editedTask.setTaskStatus(taskStatus);
        }
        else
            throw new SpringTodoException("User is not the owner of the task");


    }
    @Transactional(readOnly = true)
    public List<TaskResponse> getUsersTasksByTaskStatus(TaskStatus taskstatus)
    {
        List<Task> tasks = taskRepository.findAllByTaskStatus(taskstatus);

        return tasks.stream()
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getUsersTasksByTaskStatusAndUsername(TaskStatus taskstatus, String username)
    {
        List<Task> tasks = taskRepository.findAllByTaskStatusAndUserName(taskstatus,username);

        return tasks.stream()
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTask(Long taskId)
    {
        if (checkIfUserIsOwner(taskId))
            taskRepository.deleteById(taskId);
        else
            throw new SpringTodoException("User is not the owner of the task");
    }
    private boolean checkIfUserIsOwner(Long taskId)
    {
        User currentUser = authService.getCurrentUser();
        Task deletedTask = taskRepository.findById(taskId).orElseThrow(() -> new SpringTodoException("No such task found"));

        if((deletedTask.getCreatedBy().equals(currentUser.getUsername())) || (deletedTask.getUserName().equals(currentUser.getUsername())))
            return true;
        else
            return false;
    }

}
