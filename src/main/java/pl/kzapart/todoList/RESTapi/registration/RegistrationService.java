package pl.kzapart.todoList.RESTapi.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.todoList.RESTapi.appuser.AppUser;
import pl.kzapart.todoList.RESTapi.appuser.AppUserRole;
import pl.kzapart.todoList.RESTapi.appuser.AppUserService;
import pl.kzapart.todoList.RESTapi.registration.token.ConfirmationToken;
import pl.kzapart.todoList.RESTapi.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
        emailValidator.test(request.getEmail());
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail)
        {
            throw new IllegalStateException("Email not valid");
        }
        return appUserService.signUpUser(new AppUser(request.getFirstname(),request.getLastname(),request.getEmail(), request.getPassword(), AppUserRole.USER));
    }
    @Transactional
    public String confirmToken(String token)
    {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(()-> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null)
        {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now()))
        {
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUSer(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }

}
