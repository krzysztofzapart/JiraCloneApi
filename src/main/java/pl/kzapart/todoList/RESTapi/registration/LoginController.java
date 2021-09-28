package pl.kzapart.todoList.RESTapi.registration;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest)
    {

    }
}
