package pl.kzapart.todoList.RESTapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kzapart.todoList.RESTapi.model.Comment;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{
    List<Comment> findByTask(Task task);

    List<Comment> findByUser(User user);
}
