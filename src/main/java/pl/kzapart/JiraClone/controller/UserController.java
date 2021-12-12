package pl.kzapart.JiraClone.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kzapart.JiraClone.dto.TeamDto;
import pl.kzapart.JiraClone.service.UserService;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserService userService;

    @GetMapping("/getTeams")
    public ResponseEntity<Set<TeamDto>> getUserTeams()
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersTeams());
    }
}
