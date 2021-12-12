package pl.kzapart.JiraClone.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.JiraClone.dto.CommentDto;
import pl.kzapart.JiraClone.exceptions.SpringTodoException;
import pl.kzapart.JiraClone.mapper.CommentMapper;
import pl.kzapart.JiraClone.model.Comment;
import pl.kzapart.JiraClone.model.Task;
import pl.kzapart.JiraClone.model.User;
import pl.kzapart.JiraClone.repository.CommentRepository;
import pl.kzapart.JiraClone.repository.TaskRepository;
import pl.kzapart.JiraClone.repository.UserRepository;

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
    public Comment editComment(CommentDto commentDto)
    {
        Comment editedComment = commentRepository.findById(commentDto.getCommentId()).orElseThrow(() -> new SpringTodoException("No such comment found"));
        if(checkIfUserIsOwner(editedComment))
        {
            editedComment.setText(commentDto.getText());
            return editedComment;
        }
        else throw new SpringTodoException("User is not the owner of the comment");

    }

    @Transactional
    public void deleteComment(Long commentId)
    {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new SpringTodoException("No such comment found"));

        if(checkIfUserIsOwner(comment))
            commentRepository.delete(comment);
        else
            throw new SpringTodoException("User is not the owner of the comment");

    }

    private boolean checkIfUserIsOwner(Comment comment)
    {
        User currentUser = authService.getCurrentUser();
        if(currentUser.getUsername().equals(comment.getUserName()))
            return true;
        else
            return false;
    }

}
