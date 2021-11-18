package pl.kzapart.todoList.RESTapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kzapart.todoList.RESTapi.model.Task;
import pl.kzapart.todoList.RESTapi.model.Team;
import pl.kzapart.todoList.RESTapi.model.dict.TaskStatus;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByTeam(Team team);
    List<Task> findAllByUserName(java.lang.String userName);
    List<Task> findAllByTaskStatus(TaskStatus status);
    List<Task> findAllByTaskStatusAndUserName(TaskStatus status, String username);

}
