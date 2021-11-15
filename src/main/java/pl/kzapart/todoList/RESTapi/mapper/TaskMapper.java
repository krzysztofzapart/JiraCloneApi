package pl.kzapart.todoList.RESTapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kzapart.todoList.RESTapi.dto.TaskRequest;
import pl.kzapart.todoList.RESTapi.dto.TaskResponse;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.Team;
import pl.kzapart.todoList.RESTapi.model.User;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "taskRequest.description")
    @Mapping(target = "taskName", source = "taskRequest.taskName")
    Task mapForAdmin(TaskRequest taskRequest, Team team, User user);

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "taskRequest.description")
    @Mapping(target = "taskName", source = "taskRequest.taskName")
    @Mapping(target = "userName", source = "user.username")
    Task map(TaskRequest taskRequest, Team team, User user);


    @Mapping(target = "id", source = "taskId")
    @Mapping(target = "teamName", source = "team.name")
    @Mapping(target = "userName", source = "task.userName")
    TaskResponse mapToDto(Task task);
}
