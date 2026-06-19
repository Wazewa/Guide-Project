package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandmarkCategoryRepository extends JpaRepository<LandmarkCategory, Long> {
}
