package pl.kzapart.JiraClone.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kzapart.JiraClone.dto.CommentDto;
import pl.kzapart.JiraClone.service.CommentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CommentDto commentDto)
    {
        commentService.createComment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/by-task/{taskId}")
    public List<CommentDto> getCommentsByTask(@PathVariable Long taskId)
    {
        return commentService.getCommentsByTask(taskId);
    }
    @GetMapping("/by-user/{taskId}")
    public List<CommentDto> getCommentsByUser(@PathVariable String username)
    {
        return commentService.getCommentsByUsername(username);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId)
    {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> editComment(@RequestBody CommentDto commentDto)
    {
        commentService.editComment(commentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
