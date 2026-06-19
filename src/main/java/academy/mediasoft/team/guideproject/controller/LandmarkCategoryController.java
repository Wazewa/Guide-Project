package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.LandmarkCategoryDto;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import academy.mediasoft.team.guideproject.repository.LandmarkCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class LandmarkCategoryController {

    private final LandmarkCategoryRepository landmarkCategoryRepository;

    @GetMapping
    public List<LandmarkCategory> getAllCategories() {
        return landmarkCategoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public LandmarkCategory getCategoryById(@PathVariable Long id) {
        return landmarkCategoryRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addCategory(@RequestBody LandmarkCategoryDto landmarkCategoryDto) {
        LandmarkCategory landmarkCategory = LandmarkCategory.builder().
                name(landmarkCategoryDto.name()).
                build();
        landmarkCategoryRepository.save(landmarkCategory);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody LandmarkCategoryDto landmarkCategoryDto) {
        LandmarkCategory landmarkCategory = LandmarkCategory.builder().
                id(id).
                name(landmarkCategoryDto.name()).
                build();
        if(landmarkCategoryRepository.existsById(id)) {
            landmarkCategoryRepository.save(landmarkCategory);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        if(landmarkCategoryRepository.existsById(id)) {
            landmarkCategoryRepository.deleteById(id);
        }
    }
}
