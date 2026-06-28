package academy.mediasoft.team.guideproject.repository;

import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandmarkCategoryRepository extends JpaRepository<LandmarkCategory, Long> {
    boolean existsByName(@NotBlank(message = "Поле категории достопримечательности должно быть непустым") @Size(min = 4, max = 64, message = "Категория достопримечательности должна быть от 4 букв") String name);
}
