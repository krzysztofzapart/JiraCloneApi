package pl.kzapart.todoList.RESTapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.todoList.RESTapi.dto.CommentDto;
import pl.kzapart.todoList.RESTapi.exceptions.SpringTodoException;
import pl.kzapart.todoList.RESTapi.mapper.CommentMapper;
import pl.kzapart.todoList.RESTapi.model.Comment;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.User;
import pl.kzapart.todoList.RESTapi.repository.CommentRepository;
import pl.kzapart.todoList.RESTapi.repository.TaskRepository;
import pl.kzapart.todoList.RESTapi.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    @Transactional
    @Procedure
    public Comment createComment(CommentDto commentDto)
    {
        User current = authService.getCurrentUser();
        Task task = taskRepository.findById(commentDto.getTaskId()).orElseThrow(()-> new IllegalStateException("Task doesnt exist"));

        Comment save = commentRepository.save(commentMapper.map(commentDto,current,task));

        return save;
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new SpringTodoException("No such task found"));
        List<Comment> comments = commentRepository.findByTask(task);

        return comments
                .stream()
                .map(commentMapper::mapCommentToDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByUsername(String username) {
        List<Comment> comments = commentRepository.findByUserName(username);

        return comments
                .stream()
                .map(commentMapper::mapCommentToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void editComment(CommentDto commentDto)
    {
        Comment editedComment = commentRepository.findById(commentDto.getCommentId()).orElseThrow(() -> new SpringTodoException("No such comment found"));

        editedComment.setText(commentDto.getText());
    }

    @Transactional
    public void deleteComment(Long commentId)
    {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new SpringTodoException("No such comment found"));
        commentRepository.delete(comment);
    }

}
