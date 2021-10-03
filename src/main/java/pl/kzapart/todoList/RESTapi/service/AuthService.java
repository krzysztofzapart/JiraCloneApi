package pl.kzapart.todoList.RESTapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.todoList.RESTapi.dto.RegisterRequest;
import pl.kzapart.todoList.RESTapi.model.NotificationEmail;
import pl.kzapart.todoList.RESTapi.model.User;
import pl.kzapart.todoList.RESTapi.model.VerificationToken;
import pl.kzapart.todoList.RESTapi.repository.UserRepository;
import pl.kzapart.todoList.RESTapi.repository.VerificationTokenRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private static final String EMAIL_BODY = "Thank You for signing up! Click on the verification link to activate your account: "+
            "http://localhost:8080/api/v1/auth/accountVerification/";


    @Transactional
    public void signup(RegisterRequest registerRequest)
    {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Activate Your Account",user.getEmail(),EMAIL_BODY+token));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
