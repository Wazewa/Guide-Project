package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByPersonIdAndLandmarkId(Long personId, Long landmarkId);
}
