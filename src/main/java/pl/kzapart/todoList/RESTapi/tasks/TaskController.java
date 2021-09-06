package pl.kzapart.todoList.RESTapi.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    @PostMapping("/add")
    public String saveTask(@RequestBody Task task)
    {
        return taskService.saveTask(task);
    }

}
