package pl.kzapart.todoList.RESTapi.global;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.kzapart.todoList.RESTapi.appuser.AppUser;
import pl.kzapart.todoList.RESTapi.appuser.AppUserService;

@Component
@RequiredArgsConstructor
public class GlobalController {

    private final AppUserService appUserService;

    private AppUser user;

    public AppUser getLoginUser(){
        if(user == null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            user = appUserService.findByEmail(auth.getName());
        }
        return user;
    }

}
