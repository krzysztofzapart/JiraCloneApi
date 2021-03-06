package pl.kzapart.JiraClone.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
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
