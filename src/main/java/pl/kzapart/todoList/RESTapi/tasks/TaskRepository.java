package pl.kzapart.todoList.RESTapi.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTodoStatus(String status);
    List<Task> findByUserIDAndTodoStatus(long userID, String status);

}
