package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.LandmarkDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import academy.mediasoft.team.guideproject.repository.LandmarkCategoryRepository;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LandmarkService {

    private final LandmarkRepository landmarkRepository;
    private final LandmarkCategoryRepository landmarkCategoryRepository;
    private final RatingService ratingService;

    @Transactional(readOnly = true)
    public List<LandmarkDto> getAllLandmarks() {

        return landmarkRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public LandmarkDto getLandmarkById(Long id) {

        Double averageRating = ratingService.getAverageRatingForLandmark(id);

        Landmark landmark = landmarkRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Достопримечательность не найдена!"));

        return toDto(landmark, averageRating);

    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public void deleteLandmark(Long id) {
        if(!landmarkRepository.existsById(id)){
            throw new RuntimeException("Достопримечательность не найден!");
        }
        landmarkRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LandmarkDto> getNearbyLandmarkOnRadiusAndLimit(Double latitude, Double longitude,
                                                               Integer radius, Integer limit) {

        List<Landmark> nearbyLandmarks = landmarkRepository.findNearbyLandmarkByRadiusAndLimit(latitude, longitude, radius, limit);
        return nearbyLandmarks.stream().map(this::toDto).toList();
    }

    private LandmarkDto toDto(Landmark landmark, Double averageRating) {
        return new LandmarkDto(
                landmark.getId(),
                landmark.getName(),
                landmark.getCoordinates(),
                landmark.getDescription(),
                landmark.getAddress(),
                landmark.getCategory().getId(),
                averageRating
        );
    }

    private LandmarkDto toDto(Landmark landmark) {
        return toDto(landmark, 0.0);
    }
}
