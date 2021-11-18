package pl.kzapart.todoList.RESTapi.model;

import lombok.*;
import pl.kzapart.todoList.RESTapi.model.dict.TaskPriority;
import pl.kzapart.todoList.RESTapi.model.dict.TaskStatus;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private java.lang.String taskName;
    private java.lang.String description;
    private java.lang.String url;
    private Instant createdDate;
    private java.lang.String userName;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;
    private String createdBy;

    @ManyToOne()
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Team team;

    @OneToMany(mappedBy = "task",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments;
}