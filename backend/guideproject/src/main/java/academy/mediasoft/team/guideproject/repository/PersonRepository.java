package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

    Optional<Person> findByEmail(String email);
}
