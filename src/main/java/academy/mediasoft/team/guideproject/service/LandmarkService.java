package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.LandmarkDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import academy.mediasoft.team.guideproject.repository.LandmarkCategoryRepository;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LandmarkService {

    private final LandmarkRepository landmarkRepository;
    private final LandmarkCategoryRepository landmarkCategoryRepository;

    public List<LandmarkDto> getAllLandmarks() {

        return landmarkRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public LandmarkDto getLandmarkById(Long id) {

        return toDto(landmarkRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Достопримечательность не найдена!")));
    }

    public LandmarkDto addLandmark(LandmarkDto landmarkDto) {

        LandmarkCategory category = landmarkCategoryRepository.findById(landmarkDto.landmarkCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория не найдена!"));

        Landmark landmark = Landmark.builder().
                name(landmarkDto.name()).
                coordinates(landmarkDto.coordinates()).
                description(landmarkDto.description()).
                address(landmarkDto.address()).
                category(category).
                build();

        Landmark createdLandmark = landmarkRepository.save(landmark);

        return toDto(createdLandmark);
    }

    public LandmarkDto updateLandmark(Long id, LandmarkDto landmarkDto) {
        landmarkRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Достопримечательность не найден!"));

        LandmarkCategory category = landmarkCategoryRepository.findById(landmarkDto.landmarkCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория не найдена!"));

        Landmark landmark = Landmark.builder().
                id(id).
                name(landmarkDto.name()).
                coordinates(landmarkDto.coordinates()).
                description(landmarkDto.description()).
                address(landmarkDto.address()).
                category(category).
                build();

        Landmark updatedLandmark = landmarkRepository.save(landmark);

        return toDto(updatedLandmark);
    }

    public void deleteLandmark(Long id) {
        if(!landmarkRepository.existsById(id)){
            throw new RuntimeException("Достопримечательность не найден!");
        }
        landmarkRepository.deleteById(id);
    }

    private LandmarkDto toDto(Landmark landmark) {
        return new LandmarkDto(
                landmark.getId(),
                landmark.getName(),
                landmark.getCoordinates(),
                landmark.getDescription(),
                landmark.getAddress(),
                landmark.getCategory().getId()
        );
    }
}
