package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.LandmarkCategoryDto;
import academy.mediasoft.team.guideproject.repository.LandmarkCategoryRepository;
import academy.mediasoft.team.guideproject.service.LandmarkCategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class LandmarkCategoryController {

    private final LandmarkCategoryService categoryService;

    @GetMapping
    public List<LandmarkCategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LandmarkCategoryDto> getCategoryById(@PathVariable Long id) {

        LandmarkCategoryDto categoryDto = categoryService.getCategoryById(id);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @PostMapping
    public ResponseEntity<LandmarkCategoryDto> addCategory(@RequestBody @Valid LandmarkCategoryDto categoryDto) {

        LandmarkCategoryDto createdCategory = categoryService.addCategory(categoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandmarkCategoryDto> updateCategory(@PathVariable Long id,
                               @RequestBody @Valid LandmarkCategoryDto landmarkCategoryDto) {

        LandmarkCategoryDto updatedCategory = categoryService.updateCategory(id, landmarkCategoryDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
