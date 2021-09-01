package pl.kzapart.todoList.RESTapi.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kzapart.todoList.RESTapi.appuser.AppUser;
import pl.kzapart.todoList.RESTapi.appuser.AppUserRole;
import pl.kzapart.todoList.RESTapi.appuser.AppUserService;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        emailValidator.test(request.getEmail());
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail)
        {
            throw new IllegalStateException("Email not valid");
        }
        return appUserService.signUpUser(new AppUser(request.getFirstname(),request.getLastname(),request.getEmail(), request.getPassword(), AppUserRole.USER));
    }
}
