package pl.kzapart.todoList.RESTapi.appuser;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface AppUserRepository {

    Optional<AppUser> findByEmail(String mail);
}
