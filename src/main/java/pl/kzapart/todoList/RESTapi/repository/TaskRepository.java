package pl.kzapart.todoList.RESTapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kzapart.todoList.RESTapi.model.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
