package pl.kzapart.JiraClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kzapart.JiraClone.model.Task;
import pl.kzapart.JiraClone.model.Team;
import pl.kzapart.JiraClone.model.dict.TaskStatus;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByTeam(Team team);
    List<Task> findAllByUserName(java.lang.String userName);
    List<Task> findAllByTaskStatus(TaskStatus status);
    List<Task> findAllByTaskStatusAndUserName(TaskStatus status, String username);

}
