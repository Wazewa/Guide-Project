package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.LandmarkDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import academy.mediasoft.team.guideproject.repository.LandmarkCategoryRepository;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landmarks")
@AllArgsConstructor
public class LandmarkController {

    private final LandmarkRepository landmarkRepository;
    private final LandmarkCategoryRepository categoryRepository;

    @GetMapping
    public List<Landmark> getAllLandmarks() {
        return landmarkRepository.findAll();
    }

    @GetMapping("/{id}")
    public Landmark getLandmarkById(@PathVariable Long id) {
        return landmarkRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addLandmark(@RequestBody LandmarkDto landmarkDto) {
        LandmarkCategory category = categoryRepository.findById(landmarkDto.landmarkCategoryId())
                .orElse(null);

        Landmark landmark = Landmark.builder().
                name(landmarkDto.name()).
                coordinates(landmarkDto.coordinates()).
                description(landmarkDto.description()).
                address(landmarkDto.address()).
                category(category).
                build();
        landmarkRepository.save(landmark);
    }

    @PutMapping("/{id}")
    public void updateLandmark(@PathVariable Long id, @RequestBody LandmarkDto landmarkDto) {
        LandmarkCategory category = categoryRepository.findById(landmarkDto.landmarkCategoryId())
                .orElse(null);

        Landmark landmark = Landmark.builder().
                id(id).
                name(landmarkDto.name()).
                coordinates(landmarkDto.coordinates()).
                description(landmarkDto.description()).
                address(landmarkDto.address()).
                category(category).
                build();
        if (landmarkRepository.existsById(id)) {
            landmarkRepository.save(landmark);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteLandmark(@PathVariable Long id) {
        if (landmarkRepository.existsById(id)) {
            landmarkRepository.deleteById(id);
        }
    }
}

