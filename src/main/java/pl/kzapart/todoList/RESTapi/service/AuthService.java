package pl.kzapart.todoList.RESTapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.todoList.RESTapi.dto.AuthenticationResponse;
import pl.kzapart.todoList.RESTapi.dto.LoginRequest;
import pl.kzapart.todoList.RESTapi.dto.RegisterRequest;
import pl.kzapart.todoList.RESTapi.exceptions.SpringTodoException;
import pl.kzapart.todoList.RESTapi.model.NotificationEmail;
import pl.kzapart.todoList.RESTapi.model.User;
import pl.kzapart.todoList.RESTapi.model.UserProfile;
import pl.kzapart.todoList.RESTapi.model.VerificationToken;
import pl.kzapart.todoList.RESTapi.repository.UserProfileRepository;
import pl.kzapart.todoList.RESTapi.repository.UserRepository;
import pl.kzapart.todoList.RESTapi.repository.VerificationTokenRepository;
import pl.kzapart.todoList.RESTapi.security.JwtProvider;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserProfileRepository userProfileRepository;


    public void signup(RegisterRequest registerRequest) {
       //create user
        User user = new User();
        //create user profile
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstname(registerRequest.getUsername());

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        user.setUserProfile(userProfile);



        //verify if user exist in database;
        Optional<User> ifExist = userRepository.findByUsername(registerRequest.getUsername());
        if(ifExist.isPresent())
            throw new IllegalStateException("User already exist!");
        else
        {
            userProfileRepository.save(userProfile);
            userRepository.save(user);

        }


        //generating verification token
        String token = generateVerificationToken(user);

        //sending verification email
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up!, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/v1/auth/accountVerification/" + token));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpirationTime(Instant.now().plusSeconds(900));
        verificationTokenRepository.save(verificationToken);
        return token;
    }
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        if(Instant.now().isAfter(verificationToken.get().getExpirationTime()))
        {
            throw new SpringTodoException("Token has expired");
        }

        fetchUserAndEnable(verificationToken.orElseThrow(() -> new IllegalStateException("Invalid Token")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }
}
