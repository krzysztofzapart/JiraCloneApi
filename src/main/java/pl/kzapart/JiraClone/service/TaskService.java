package pl.kzapart.JiraClone.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.JiraClone.dto.TaskRequest;
import pl.kzapart.JiraClone.mapper.TaskMapper;
import pl.kzapart.JiraClone.model.dict.TaskStatus;
import pl.kzapart.JiraClone.dto.TaskResponse;
import pl.kzapart.JiraClone.exceptions.SpringTodoException;
import pl.kzapart.JiraClone.model.Task;
import pl.kzapart.JiraClone.model.Team;
import pl.kzapart.JiraClone.model.User;
import pl.kzapart.JiraClone.repository.TaskRepository;
import pl.kzapart.JiraClone.repository.TeamRepository;
import pl.kzapart.JiraClone.repository.UserRepository;

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
        if(checkIfUserIsInTeam(taskRequest))
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
        else
            throw new SpringTodoException("The selected user is not a member of this team ");


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
    public Task editTask(TaskRequest taskRequest)
    {
        if (checkIfUserIsOwner(taskRequest.getTaskId()))
        {
            Task editedTask = taskRepository.findById(taskRequest.getTaskId()).orElseThrow(() -> new SpringTodoException("No such task found"));
            taskRepository.save(editedTask);
            editedTask.setTaskName(taskRequest.getTaskName());
            editedTask.setDescription(taskRequest.getDescription());
            editedTask.setUrl(taskRequest.getUrl());
            return editedTask;
        }
        else
            throw new SpringTodoException("User is not the owner of the task");


    }
    @Transactional
    public Task editTaskStatus(TaskStatus taskStatus, Long taskId)
    {
        if (checkIfUserIsOwner(taskId))
        {
            Task editedTask = taskRepository.findById(taskId).orElseThrow(() -> new SpringTodoException("No such task found"));
            taskRepository.save(editedTask);
            editedTask.setTaskStatus(taskStatus);
            return editedTask;
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
    private boolean checkIfUserIsInTeam(TaskRequest taskRequest)
    {
        User user = userRepository.findByUsername(taskRequest.getUsername()).orElseThrow(()-> new SpringTodoException("No such user found"));
        Team team = teamRepository.findByName(taskRequest.getTeamName()).orElseThrow(()-> new IllegalStateException("No such team found"));
        if(team.getUsers().contains(user))
            return true;
        else
            return false;
    }
}
