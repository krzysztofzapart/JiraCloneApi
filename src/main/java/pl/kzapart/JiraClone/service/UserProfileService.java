package pl.kzapart.JiraClone.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kzapart.JiraClone.exceptions.SpringTodoException;
import pl.kzapart.JiraClone.model.User;
import pl.kzapart.JiraClone.model.UserProfile;
import pl.kzapart.JiraClone.repository.UserProfileRepository;
import pl.kzapart.JiraClone.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public UserProfile getUserProfileByUser(Long userId)
    {
        User user = userRepository.findById(userId).orElseThrow( () -> new SpringTodoException("No such user found"));
        UserProfile userProfile = user.getUserProfile();
        return userProfile;
    }
    public UserProfile getCurrentUserProfile()
    {
        User currentUser = authService.getCurrentUser();
        UserProfile userProfile = currentUser.getUserProfile();
        return userProfile;
    }

    @Transactional
    public void editProfile(UserProfile userProfile)
    {
        UserProfile editedProfile = userProfileRepository.findById(userProfile.getProfileId()).orElseThrow( () -> new SpringTodoException("No such profile found"));
        editedProfile.setFirstname(userProfile.getFirstname());
        editedProfile.setLastname(userProfile.getLastname());
        editedProfile.setDescription(userProfile.getDescription());
        editedProfile.setJobName(userProfile.getJobName());
        //TODO FILE UPLOADING
        editedProfile.setAvatar(userProfile.getAvatar());
    }

}
