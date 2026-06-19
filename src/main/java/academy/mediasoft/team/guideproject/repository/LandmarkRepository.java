package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Long> {
}
