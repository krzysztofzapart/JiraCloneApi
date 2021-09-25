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
        taskRepository.save(task);

        return "Add task!";
    }
}
