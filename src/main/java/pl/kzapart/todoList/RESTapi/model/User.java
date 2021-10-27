package pl.kzapart.todoList.RESTapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
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
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Team> teams;
    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JoinColumn(name = "profileId")
    private UserProfile userProfile;

}