package pl.kzapart.todoList.RESTapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String text;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JoinColumn(name = "taskId", referencedColumnName = "taskId")
    private Task task;
    @ManyToOne(fetch = FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
