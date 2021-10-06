package pl.kzapart.todoList.RESTapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kzapart.todoList.RESTapi.dto.CommentDto;
import pl.kzapart.todoList.RESTapi.model.Comment;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "task", source = "task")
    Comment map(CommentDto commentDto, User user, Task task);


    @Mapping(target = "taskId", expression = "java(comment.getTask().getTaskId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentDto mapCommentToDto(Comment comment);
}
