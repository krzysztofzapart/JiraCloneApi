package pl.kzapart.todoList.RESTapi.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kzapart.todoList.RESTapi.registration.token.ConfirmationToken;
import pl.kzapart.todoList.RESTapi.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));

    }
    public String signUpUser(AppUser appUser){
        boolean isExists = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();
        if(isExists)
        {
            throw new IllegalStateException("email already used");
        }
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        String tokenUuid = UUID.randomUUID().toString();
        ConfirmationToken token = new ConfirmationToken(tokenUuid, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
        tokenService.saveConfirmationToken(token);
        return tokenUuid;
    }

    public int enableAppUSer(String email)
    {
        return appUserRepository.enableAppUser(email);
    }

    public AppUser findAppUserById(Long id)
    {
        return appUserRepository.findAppUserById(id).orElseThrow(()-> new IllegalStateException("No such user"));
    }
}
