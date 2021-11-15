package pl.kzapart.todoList.RESTapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long profileId;
    private String firstname;
    private String lastname;
    private String description;
    private String jobName;
    private byte[] avatar;

    @OneToOne()
    @JoinColumn(name = "userId")
    private User user;
}
