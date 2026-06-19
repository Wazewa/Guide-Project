package academy.mediasoft.team.guideproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

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
    @NotEmpty(message = "Поле имени должно быть непустым")
    @Size(min = 2, max = 40, message = "Имя должно быть от 2 букв")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Поле фамилии должно быть непустым")
    @Size(min = 2, max = 40, message = "Фамилия должна быть от 2 букв")
    private String surname;

    @Column(nullable = false)
    private String hashPassword;

    @Column(unique = true)
    @Email
    @NotEmpty(message = "Поле почты должно быть непустым")
    private String email;

    @OneToMany(mappedBy = "person")
    private List<Review> reviews;

    @OneToMany(mappedBy = "person")
    private List<Rating> ratings;
}
