package pl.kzapart.JiraClone.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String email;
    private Instant created;
    private boolean enabled;

    @ManyToMany(mappedBy = "users")
    private Set<Team> teams;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private UserProfile userProfile;



}