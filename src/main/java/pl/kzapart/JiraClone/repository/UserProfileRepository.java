package pl.kzapart.JiraClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kzapart.JiraClone.model.User;
import pl.kzapart.JiraClone.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

    UserProfile findAllByUser(User user);
}
