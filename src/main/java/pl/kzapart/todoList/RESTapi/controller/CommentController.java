package pl.kzapart.todoList.RESTapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kzapart.todoList.RESTapi.dto.CommentDto;
import pl.kzapart.todoList.RESTapi.model.Comment;
import pl.kzapart.todoList.RESTapi.service.CommentService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CommentDto commentDto)
    {
        commentService.createPost(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
