package pl.kzapart.JiraClone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.JiraClone.dto.AuthenticationResponse;
import pl.kzapart.JiraClone.dto.LoginRequest;
import pl.kzapart.JiraClone.dto.RegisterRequest;
import pl.kzapart.JiraClone.exceptions.SpringTodoException;
import pl.kzapart.JiraClone.model.NotificationEmail;
import pl.kzapart.JiraClone.model.User;
import pl.kzapart.JiraClone.model.UserProfile;
import pl.kzapart.JiraClone.model.VerificationToken;
import pl.kzapart.JiraClone.repository.UserProfileRepository;
import pl.kzapart.JiraClone.repository.UserRepository;
import pl.kzapart.JiraClone.repository.VerificationTokenRepository;
import pl.kzapart.JiraClone.security.JwtProvider;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserProfileRepository userProfileRepository;


    public String signup(RegisterRequest registerRequest) {
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
        if(ifExist.isPresent()) {
            return "User already exists";
        }
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
                "http://https://jira-kanban-clone.herokuapp.com/api/v1/auth/accountVerification/" + token));
        System.out.println(token);
        log.info(token);
        return "In case the post server crashes, this is your activation token: "+ token;
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
