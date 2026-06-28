package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.Landmark;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Long> {

    @Query(value = """
                    SELECT * FROM Landmark
                      WHERE ST_DWithin(
                            coordinates,
                            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius)
                      ORDER BY ST_Distance(
                            coordinates, 
                            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) 
                      LIMIT :limit
                    """, nativeQuery = true)
    List<Landmark> findNearbyLandmarkByRadiusAndLimit(@Param("latitude") Double latitude,
                                                      @Param("longitude")Double longitude,
                                                      @Param("radius") Integer radius,
                                                      @Param("limit") Integer limit);

    boolean existsByName(@NotBlank(message = "Поле имени достопримечательности должно быть непустым") @Size(min = 2, max = 64, message = "Название достопримечательности должно быть от 2 букв") String name);
}
