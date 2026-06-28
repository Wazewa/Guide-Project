package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.Person;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

    Optional<Person> findByEmail(String email);

    boolean existsByEmail(@Email @NotBlank(message = "Поле почты должно быть непустым") String email);
}
