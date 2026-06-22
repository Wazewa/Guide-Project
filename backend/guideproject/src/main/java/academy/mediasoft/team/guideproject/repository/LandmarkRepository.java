package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Long> {

    @Query(value = "SELECT * FROM Landmark LIMIT :limit", nativeQuery = true)
    List<Landmark> findNearbyLandmarkOnRadiusAndLimit(@Param("latitude") Double latitude,
                                                      @Param("longitude")Double longitude,
                                                      @Param("radius") Integer radius,
                                                      @Param("limit") Integer limit);
}
