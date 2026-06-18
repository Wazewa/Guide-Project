package academy.mediasoft.team.guideproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "person_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String hashPassword;
    @Column(unique = true, nullable = false)
    private String email;
}
