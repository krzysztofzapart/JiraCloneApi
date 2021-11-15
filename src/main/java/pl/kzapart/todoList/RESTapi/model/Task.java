package pl.kzapart.todoList.RESTapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String taskName;
    private String description;
    private String url;
    private Instant createdDate;
    private String userName;


    @ManyToOne()
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Team team;

    @OneToMany(mappedBy = "task",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments;
}