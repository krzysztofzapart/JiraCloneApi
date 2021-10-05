package pl.kzapart.todoList.RESTapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import pl.kzapart.todoList.RESTapi.dto.CommentDto;
import pl.kzapart.todoList.RESTapi.mapper.CommentMapper;
import pl.kzapart.todoList.RESTapi.model.Comment;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.User;
import pl.kzapart.todoList.RESTapi.repository.CommentRepository;
import pl.kzapart.todoList.RESTapi.repository.TaskRepository;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;

    @Transactional
    public Comment createPost(CommentDto commentDto)
    {
        User current = authService.getCurrentUser();
        Task task = taskRepository.findById(commentDto.getTaskId()).orElseThrow(()-> new IllegalStateException("Post doesnt exist"));

        Comment save = commentRepository.save(commentMapper.map(commentDto,current,task));

        return save;
    }
}
