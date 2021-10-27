package pl.kzapart.todoList.RESTapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kzapart.todoList.RESTapi.dto.TeamDto;
import pl.kzapart.todoList.RESTapi.model.Team;
import pl.kzapart.todoList.RESTapi.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

}
