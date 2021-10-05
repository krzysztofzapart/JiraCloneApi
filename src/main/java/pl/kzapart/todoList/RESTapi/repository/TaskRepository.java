package pl.kzapart.todoList.RESTapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.Team;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByTeam(Team team);
}
