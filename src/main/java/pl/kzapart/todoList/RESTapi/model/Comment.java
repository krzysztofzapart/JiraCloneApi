package pl.kzapart.todoList.RESTapi.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String text;
    private Instant createdDate;

    @ManyToOne()
    @JoinColumn(name = "taskId", referencedColumnName = "taskId")
    private Task task;

    private String userName;
}
