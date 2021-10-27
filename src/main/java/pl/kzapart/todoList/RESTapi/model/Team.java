package pl.kzapart.todoList.RESTapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;


@Data
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
    @OneToMany(fetch = FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Task> tasks;
    private Instant createdDate;
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Set<User> users;


}
