package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> id(Long id);
}
