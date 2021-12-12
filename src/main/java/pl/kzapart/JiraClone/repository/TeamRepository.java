package pl.kzapart.JiraClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kzapart.JiraClone.model.Team;
import pl.kzapart.JiraClone.model.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> findByName(String name);
    Set<Team> findByUsers(User user);

}
