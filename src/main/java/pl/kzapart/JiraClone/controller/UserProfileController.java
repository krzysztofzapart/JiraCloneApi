package pl.kzapart.JiraClone.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kzapart.JiraClone.model.UserProfile;
import pl.kzapart.JiraClone.service.UserProfileService;

@RestController
@RequestMapping("/api/v1/profile")
@AllArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{userId}")
    ResponseEntity<UserProfile> getUserProfileByUser(@PathVariable Long userId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.getUserProfileByUser(userId));
    }

    @GetMapping("/current")
    ResponseEntity<UserProfile> getCurrentUserProfile()
    {
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.getCurrentUserProfile());
    }
    @PutMapping
    ResponseEntity<Void> editProfile(@RequestBody UserProfile userProfile)
    {
        userProfileService.editProfile(userProfile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
