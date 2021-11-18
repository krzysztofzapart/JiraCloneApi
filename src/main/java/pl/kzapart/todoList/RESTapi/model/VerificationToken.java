package pl.kzapart.todoList.RESTapi.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String token;
    @OneToOne(fetch = FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    private User user;
    private Instant expirationTime;
}
