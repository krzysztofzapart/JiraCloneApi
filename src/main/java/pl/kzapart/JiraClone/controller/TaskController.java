package pl.kzapart.JiraClone.controller;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kzapart.JiraClone.dto.TaskRequest;
import pl.kzapart.JiraClone.dto.TaskResponse;
import pl.kzapart.JiraClone.model.dict.TaskStatus;
import pl.kzapart.JiraClone.service.TaskService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tasks")
@DynamicUpdate
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TaskRequest taskRequest)
    {
        taskService.save(taskRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("by-team/{id}")
    public ResponseEntity<List<TaskResponse>> getTasksByTeam(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasksByTeam(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<TaskResponse>> getTasksByUser(java.lang.String name)
    {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasksByUser(name));
    }
    @GetMapping("/my-tasks")
    public ResponseEntity<List<TaskResponse>> getMyTasks()
    {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getMyTasks());
    }

    @PutMapping
    public ResponseEntity<java.lang.String> editTask(@RequestBody TaskRequest taskRequest)
    {
        taskService.editTask(taskRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/edit-task-status/{taskId}")
    public ResponseEntity<java.lang.String> editTaskStatus(@RequestBody TaskStatus taskStatus, @PathVariable Long taskId)
    {
        taskService.editTaskStatus(taskStatus, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/by-status/{taskStatus}")
    public ResponseEntity<List<TaskResponse>> getUsersTasksByTaskStatus(@PathVariable TaskStatus taskStatus)
    {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getUsersTasksByTaskStatus(taskStatus));
    }
    @GetMapping("/by-status/{taskStatus}/user/{username}")
    public ResponseEntity<List<TaskResponse>> getUsersTasksByTaskStatusAndUsername(@PathVariable TaskStatus taskStatus, @PathVariable String username)
    {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getUsersTasksByTaskStatusAndUsername(taskStatus,username));
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<java.lang.String> deleteTaskById(@PathVariable Long taskId)
    {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
