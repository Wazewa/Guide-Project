package academy.mediasoft.team.guideproject.repository;

import java.util.List;
import java.util.Optional;
import academy.mediasoft.team.guideproject.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query(value = "SELECT AVG(grade) FROM Rating WHERE landmark.id = :landmarkId")
    Double getAverageRatingById(@Param("landmarkId") Long id);

    boolean existsByPersonIdAndLandmarkId(Long personId, Long landmarkId);

    List<Rating> findByLandmarkId(Long landmarkId);
}
