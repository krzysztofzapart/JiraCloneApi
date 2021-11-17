package pl.kzapart.todoList.RESTapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kzapart.todoList.RESTapi.dto.CommentDto;
import pl.kzapart.todoList.RESTapi.exceptions.SpringTodoException;
import pl.kzapart.todoList.RESTapi.mapper.CommentMapper;
import pl.kzapart.todoList.RESTapi.model.*;
import pl.kzapart.todoList.RESTapi.repository.CommentRepository;
import pl.kzapart.todoList.RESTapi.repository.TaskRepository;
import pl.kzapart.todoList.RESTapi.repository.UserRepository;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private AuthService authService;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;
    @Test
    void shouldCreateComment() {
        //given
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(1L);
        commentDto.setText("Test");
        commentDto.setUsername("testowy");
        commentDto.setCreatedDate(Instant.now());
        commentDto.setTaskId(1L);

        Set<Team> teams = new HashSet<>();
        UserProfile userProfile = new UserProfile();
        Task task = new Task();
        task.setTaskId(1L);

        Comment saveComment = new Comment();


        //when
        Mockito.when(authService.getCurrentUser()).thenReturn(new User(1L,"testowy","passwd","testowy@test.com", Instant.now(),false,teams, userProfile));
        Mockito.when(taskRepository.findById(Mockito.any())).thenReturn(Optional.of(task));
        Mockito.when(commentRepository.save(Mockito.any())).thenReturn(saveComment);

        //then
        assertEquals(saveComment,commentService.createComment(commentDto));

    }

    @Test
    void shouldGetCommentsByTask() {
        Long taskId = 1L;
        Task task1 = Mockito.mock(Task.class);
        Task task2 = Mockito.mock(Task.class);
        //given
        Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Task()));
        Mockito.when(commentRepository.findByTask(Mockito.any())).thenReturn(Arrays.asList(
                new Comment(1L,"nowyCom",Instant.now(),task1,"test"),
                new Comment(2L,"nowyCom2",Instant.now(),task2,"test2")
        ));
        //when
        List<CommentDto> comments = commentService.getCommentsByTask(taskId);
        //then
        assertThat(comments).hasSize(2);
    }

    @Test
    void shouldGetCommentsByUsername() {
        String username = "test";
        Task task1 = Mockito.mock(Task.class);
        Task task2 = Mockito.mock(Task.class);
        //given
        Mockito.when(commentRepository.findByUserName(Mockito.any())).thenReturn(Arrays.asList(
                new Comment(1L,"nowyCom",Instant.now(),task1,"test"),
                new Comment(2L,"nowyCom2",Instant.now(),task2,"test")
        ));
        //when
        List<CommentDto> comments = commentService.getCommentsByUsername(username);
        //then
        assertThat(comments).hasSize(2);
    }

    @Test
    void shouldEditComment() {
        //given
        CommentDto editedComment = new CommentDto(1L,1L,"edytowanyTekst",Instant.now(),"testowy");
        Task task = Mockito.mock(Task.class);
        //when
        Mockito.when(commentRepository.findById(Mockito.any())).thenReturn(
            Optional.of(new Comment(1L,"Testowy",Instant.now(),task,"testowy"))
        );

        commentService.editComment(editedComment);
        Comment comment = commentRepository.findById(1L).orElseThrow(()-> new IllegalStateException("No such comment found"));
        //then
        assertEquals(editedComment.getText(),comment.getText());

    }

    @Test
    void shoulddeleteComment() {
        //given
        Task task = Mockito.mock(Task.class);
        //when
        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(
                new Comment(1L,"Testowy",Instant.now(),task,"testowy")
        )).thenReturn(null);
        //then
        commentService.deleteComment(1L);

        assertEquals(null,commentRepository.findById(1L));
    }
}