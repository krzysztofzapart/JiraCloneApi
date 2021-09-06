package pl.kzapart.todoList.RESTapi.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping(path = "/user")
    public AppUser findAppUserById(@RequestParam("id") Long id)
    {
        return appUserService.findAppUserById(id);
    }
    @GetMapping(path = "/users")
    public List<AppUser> findAllAppUsers()
    {
        return appUserService.findAllAppUsers();
    }
}
