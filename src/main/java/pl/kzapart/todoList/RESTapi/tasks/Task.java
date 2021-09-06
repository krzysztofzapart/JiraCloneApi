package pl.kzapart.todoList.RESTapi.tasks;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kzapart.todoList.RESTapi.appuser.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Task {

        @SequenceGenerator(
                name = "user_sequence",
                sequenceName = "user_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "user_sequence"
        )
        @Id
        private Long id;
        private String title;
        private String description;
        @Enumerated(EnumType.STRING)
        private TodoStatus todoStatus;
        private LocalDateTime created;
        private Long doneBy;
        private Long userID;
}
