package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.LandmarkCategoryDto;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import java.util.Optional;
import academy.mediasoft.team.guideproject.repository.LandmarkCategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LandmarkCategoryServiceTest {

    @Mock
    private LandmarkCategoryRepository categoryRepository;

    @InjectMocks
    private LandmarkCategoryService categoryService;

    @Test
    public void addLandmarkCategory_validData_save() {
        Long categoryId = 15L;

        LandmarkCategoryDto categoryDto = initializeLandmarkCategoryDto(categoryId);
        LandmarkCategory category = initializeLandmarkCategory(categoryDto);

        Mockito.when(categoryRepository.existsByName(categoryDto.name())).thenReturn(false);
        Mockito.when(categoryRepository.save(Mockito.any(LandmarkCategory.class))).thenReturn(category);

        LandmarkCategoryDto result = categoryService.addCategory(categoryDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.getId(), result.id());
        Assertions.assertEquals(category.getName(), result.name());

        Mockito.verify(categoryRepository).save(Mockito.any(LandmarkCategory.class));
    }

    @Test
    public void addLandmarkCategory_existingCategory_throwException() {
        Long categoryId = 15L;

        LandmarkCategoryDto categoryDto = initializeLandmarkCategoryDto(categoryId);

        Mockito.when(categoryRepository.existsByName(categoryDto.name())).thenReturn(true);

        Assertions.assertThrows(RuntimeException.class, () ->
                categoryService.addCategory(categoryDto));

        Mockito.verify(categoryRepository).existsByName(categoryDto.name());
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any(LandmarkCategory.class));
    }

    @Test
    public void updateLandmarkCategory_validData_save() {
        Long categoryId = 15L;

        LandmarkCategoryDto categoryDto = initializeLandmarkCategoryDto(categoryId);
        LandmarkCategory category = initializeLandmarkCategory(categoryDto);

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any(LandmarkCategory.class))).thenReturn(category);

        LandmarkCategoryDto result = categoryService.updateCategory(categoryId, categoryDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.getId(), result.id());
        Assertions.assertEquals(category.getName(), result.name());

        Mockito.verify(categoryRepository).findById(categoryId);
        Mockito.verify(categoryRepository).save(Mockito.any(LandmarkCategory.class));
    }

    @Test
    public void updateLandmarkCategory_notFoundCategory_throwException() {
        Long categoryId = 15L;

        LandmarkCategoryDto categoryDto = initializeLandmarkCategoryDto(categoryId);

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                categoryService.updateCategory(categoryId, categoryDto));

        Mockito.verify(categoryRepository).findById(categoryId);
        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any(LandmarkCategory.class));
    }

    @Test
    public void deleteLandmarkCategory_validData_delete() {
        Long categoryId = 15L;

        Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(true);
        Mockito.doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategory(categoryId);

        Mockito.verify(categoryRepository).existsById(categoryId);
        Mockito.verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    public void deleteLandmarkCategory_notFoundCategory_throwException() {
        Long categoryId = 15L;

        Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () ->
                categoryService.deleteCategory(categoryId));

        Mockito.verify(categoryRepository).existsById(categoryId);
        Mockito.verify(categoryRepository, Mockito.never()).deleteById(categoryId);
    }

    private LandmarkCategoryDto initializeLandmarkCategoryDto(Long categoryId) {
        return new LandmarkCategoryDto(
                categoryId,
                "Архитектура"
                );
    }

    private LandmarkCategory initializeLandmarkCategory(LandmarkCategoryDto categoryDto) {
        return LandmarkCategory.builder()
                .id(categoryDto.id())
                .name(categoryDto.name())
                .build();
    }
}
