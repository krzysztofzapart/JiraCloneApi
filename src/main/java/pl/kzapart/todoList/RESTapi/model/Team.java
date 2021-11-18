package pl.kzapart.todoList.RESTapi.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "team", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Task> tasks;
    private String teamOwner;
    private Instant createdDate;

    @ManyToMany()
    @JoinTable(name = "usersInTeams",
            joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<User> users;


}
