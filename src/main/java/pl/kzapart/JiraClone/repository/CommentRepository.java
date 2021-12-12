package pl.kzapart.JiraClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kzapart.JiraClone.model.Comment;
import pl.kzapart.JiraClone.model.Task;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{
    List<Comment> findByTask(Task task);

    List<Comment> findByUserName(String username);
}
