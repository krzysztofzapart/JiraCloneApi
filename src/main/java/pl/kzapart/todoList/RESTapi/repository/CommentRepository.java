package pl.kzapart.todoList.RESTapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kzapart.todoList.RESTapi.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{
}
