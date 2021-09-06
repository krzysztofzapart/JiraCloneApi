package pl.kzapart.todoList.RESTapi.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kzapart.todoList.RESTapi.appuser.AppUser;
import pl.kzapart.todoList.RESTapi.registration.RegistrationRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AppUser appUser;

    public String saveTask(Task task)
    {

        //TODO TO FIX ADDING

        task.setCreated(LocalDateTime.now());
        task.setTodoStatus(TodoStatus.TODO);
        task.setUserID(4L);

        taskRepository.save(task);


        return "Add task!";
    }
}
