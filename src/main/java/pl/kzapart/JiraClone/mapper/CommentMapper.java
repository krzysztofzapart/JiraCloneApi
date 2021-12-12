package pl.kzapart.JiraClone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kzapart.JiraClone.dto.CommentDto;
import pl.kzapart.JiraClone.model.Comment;
import pl.kzapart.JiraClone.model.Task;
import pl.kzapart.JiraClone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "task", source = "task")
    @Mapping(target = "userName", source = "user.username")
    Comment map(CommentDto commentDto, User user, Task task);


    @Mapping(target = "taskId", expression = "java(comment.getTask().getTaskId())")
    @Mapping(target = "username", source = "comment.userName")
    CommentDto mapCommentToDto(Comment comment);
}
