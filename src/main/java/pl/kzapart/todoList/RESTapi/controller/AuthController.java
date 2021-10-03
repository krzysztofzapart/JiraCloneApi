package pl.kzapart.todoList.RESTapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kzapart.todoList.RESTapi.dto.RegisterRequest;
import pl.kzapart.todoList.RESTapi.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest reqisterRequest)
    {
        authService.signup(reqisterRequest);
        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
    }
}
