package pl.kzapart.todoList.RESTapi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kzapart.todoList.RESTapi.dto.TeamDto;
import pl.kzapart.todoList.RESTapi.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
@AllArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.save(teamDto));
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams()
    {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getAllTeams());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeam(id));
    }
}

