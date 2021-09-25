package pl.kzapart.todoList.RESTapi.tasks;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kzapart.todoList.RESTapi.global.GlobalController;

import java.time.LocalDateTime;

@Controller

@RequiredArgsConstructor
public class TaskController {

    private final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;
    private final GlobalController globalController;

    @RequestMapping(value = "api/v1/task/saveTask", method = RequestMethod.POST)
    public String saveTask(@RequestBody Task task)
    {
        try{
            task.setCreated(LocalDateTime.now());
            task.setTodoStatus(TodoStatus.TODO);
            task.setUserID(globalController.getLoginUser().getId());
            taskService.saveTask(task);
        }catch (Exception e)
        {
            logger.error("save: "+e.getMessage());
        }
        return "Added Succesfuly!";
    }

}
