package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.LandmarkCategoryDto;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import academy.mediasoft.team.guideproject.repository.LandmarkCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LandmarkCategoryService {
    private final LandmarkCategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<LandmarkCategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public LandmarkCategoryDto getCategoryById(Long id) {
        return toDto(categoryRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Категории не существует!")));
    }

    @Transactional
    public LandmarkCategoryDto addCategory(LandmarkCategoryDto categoryDto) {
        if(categoryRepository.existsByName(categoryDto.name())) {
            throw new RuntimeException("Такая категория уже есть!");
        }

        LandmarkCategory category = LandmarkCategory.builder()
                .name(categoryDto.name())
                .build();

        return toDto(categoryRepository.save(category));
    }

    @Transactional
    public LandmarkCategoryDto updateCategory(Long id, LandmarkCategoryDto categoryDto) {

        categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Категория не найдена!"));

        LandmarkCategory category = LandmarkCategory.builder()
                .id(id)
                .name(categoryDto.name())
                .build();

        return toDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new RuntimeException("Категория не найдена!");
        }
        categoryRepository.deleteById(id);
    }

    private LandmarkCategoryDto toDto(LandmarkCategory category) {
        return new LandmarkCategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
