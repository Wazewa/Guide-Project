package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.LandmarkDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import academy.mediasoft.team.guideproject.repository.LandmarkCategoryRepository;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LandmarkServiceTest {

    @Mock
    private LandmarkRepository landmarkRepository;

    @Mock
    private LandmarkCategoryRepository landmarkCategoryRepository;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private LandmarkService landmarkService;

    @Test
    public void addLandmark_validData_save() {

        Long categoryId = (long) 5;

        LandmarkDto landmarkDto = initializeLandmarkDto(categoryId);
        LandmarkCategory landmarkCategory = initializeLandmarkCategory(categoryId);
        Landmark landmark = initializeLandmark(landmarkDto, landmarkCategory);

        Mockito.when(landmarkCategoryRepository.findById(categoryId)).thenReturn(Optional.of(landmarkCategory));
        Mockito.when(landmarkRepository.existsByName(landmarkDto.name())).thenReturn(false);
        Mockito.when(landmarkRepository.save(Mockito.any(Landmark.class))).thenReturn(landmark);

        LandmarkDto result = landmarkService.addLandmark(landmarkDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(landmark.getId(), result.id());
        Assertions.assertEquals(landmark.getName(), result.name());
        Assertions.assertEquals(categoryId, result.landmarkCategoryId());

        Mockito.verify(landmarkCategoryRepository).findById(categoryId);
        Mockito.verify(landmarkRepository).existsByName(landmark.getName());
        Mockito.verify(landmarkRepository).save(Mockito.any(Landmark.class));

    }

    @Test
    public void addLandmark_notFound_throwsException() {

        Long categoryId = (long) 5;

        LandmarkDto landmarkDto = initializeLandmarkDto(categoryId);
        LandmarkCategory landmarkCategory = initializeLandmarkCategory(categoryId);
        Landmark landmark = initializeLandmark(landmarkDto, landmarkCategory);

        Mockito.when(landmarkCategoryRepository.findById(categoryId)).thenReturn(Optional.of(landmarkCategory));
        Mockito.when(landmarkRepository.existsByName(landmark.getName())).thenReturn(true);

        Assertions.assertThrows(RuntimeException.class, () ->
                landmarkService.addLandmark(landmarkDto));

        Mockito.verify(landmarkRepository, Mockito.never()).save(Mockito.any(Landmark.class));
    }

    @Test
    public void getAllLandmarks_validData_findAll() {
        Long categoryId = (long) 5;

        LandmarkDto landmarkDto = initializeLandmarkDto(categoryId);
        LandmarkCategory landmarkCategory = initializeLandmarkCategory(categoryId);
        Landmark landmark = initializeLandmark(landmarkDto, landmarkCategory);

        Mockito.when(landmarkRepository.findAll()).thenReturn(List.of(landmark));

        List<LandmarkDto> result = landmarkService.getAllLandmarks();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(landmark.getName(), result.get(0).name());
        Assertions.assertEquals(categoryId, result.get(0).landmarkCategoryId());

        Mockito.verify(landmarkRepository).findAll();
    }
    @Test
    public void getLandmarkById_validData_findById() {
        Long categoryId = (long) 5;

        LandmarkDto landmarkDto = initializeLandmarkDto(categoryId);
        LandmarkCategory landmarkCategory = initializeLandmarkCategory(categoryId);
        Landmark landmark = initializeLandmark(landmarkDto, landmarkCategory);

        Mockito.when(ratingService.getAverageRatingForLandmark(landmark.getId())).thenReturn(Double.valueOf(categoryId));
        Mockito.when(landmarkRepository.findById(landmark.getId())).thenReturn(Optional.of(landmark));

        LandmarkDto result = landmarkService.getLandmarkById(landmark.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(15, result.id());
        Assertions.assertEquals(landmark.getName(), result.name());
        Assertions.assertEquals(landmark.getCoordinates(), result.coordinates());
        Assertions.assertEquals(landmark.getDescription(), result.description());
        Assertions.assertEquals(landmark.getAddress(), result.address());
        Assertions.assertEquals(landmarkCategory.getId(), result.landmarkCategoryId());

        Mockito.verify(ratingService).getAverageRatingForLandmark(landmark.getId());
        Mockito.verify(landmarkRepository).findById(landmark.getId());

    }

    @Test
    public void getLandmarkById_notFound_throwsException() {
        Long categoryId = (long) 5;

        LandmarkDto landmarkDto = initializeLandmarkDto(categoryId);
        LandmarkCategory landmarkCategory = initializeLandmarkCategory(categoryId);
        Landmark landmark = initializeLandmark(landmarkDto, landmarkCategory);

        Mockito.when(ratingService.getAverageRatingForLandmark(landmark.getId())).thenReturn(Double.valueOf(categoryId));
        Mockito.when(landmarkRepository.findById(landmark.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                landmarkService.getLandmarkById(landmark.getId()));

        Mockito.verify(ratingService).getAverageRatingForLandmark(landmark.getId());
        Mockito.verify(landmarkRepository).findById(landmark.getId());
    }

    @Test
    public void updateLandmark_validData_save() {
        Long categoryId = (long) 5;
        Long landmarkId = 15L;

        LandmarkDto landmarkDto = initializeLandmarkDto(categoryId);
        LandmarkCategory landmarkCategory = initializeLandmarkCategory(categoryId);
        Landmark landmark = initializeLandmark(landmarkDto, landmarkCategory);

        Mockito.when(landmarkRepository.findById(landmark.getId())).thenReturn(Optional.of(landmark));
        Mockito.when(landmarkCategoryRepository.findById(landmark.getCategory().getId())).thenReturn(Optional.of(landmarkCategory));
        Mockito.when(landmarkRepository.save(Mockito.any(Landmark.class))).thenReturn(landmark);

        LandmarkDto result = landmarkService.updateLandmark(landmarkId, landmarkDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(15, result.id());
        Assertions.assertEquals(landmark.getName(), result.name());
        Assertions.assertEquals(landmark.getCoordinates(), result.coordinates());
        Assertions.assertEquals(landmark.getDescription(), result.description());
        Assertions.assertEquals(landmark.getAddress(), result.address());
        Assertions.assertEquals(landmarkCategory.getId(), result.landmarkCategoryId());

        Mockito.verify(landmarkRepository).findById(landmark.getId());
        Mockito.verify(landmarkCategoryRepository).findById(landmarkCategory.getId());
        Mockito.verify(landmarkRepository).save(Mockito.any(Landmark.class));
    }

    @Test
    public void updateLandmark_notFoundLandmark_throwException() {
        Long categoryId = (long) 5;
        Long landmarkId = 15L;

        LandmarkDto landmarkDto = initializeLandmarkDto(categoryId);
        LandmarkCategory landmarkCategory = initializeLandmarkCategory(categoryId);
        Landmark landmark = initializeLandmark(landmarkDto, landmarkCategory);

        Mockito.when(landmarkRepository.findById(landmark.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                landmarkService.updateLandmark(landmarkId, landmarkDto));

        Mockito.verify(landmarkRepository).findById(landmark.getId());
        Mockito.verify(landmarkCategoryRepository, Mockito.never()).findById(landmarkCategory.getId());
        Mockito.verify(landmarkRepository, Mockito.never()).save(Mockito.any(Landmark.class));
    }

    @Test
    public void deleteLandmark_validData_deleteById() {
        Long landmarkId = 15L;

        Mockito.when(landmarkRepository.existsById(landmarkId)).thenReturn(true);
        Mockito.doNothing().when(landmarkRepository).deleteById(landmarkId);

        landmarkService.deleteLandmark(landmarkId);

        Mockito.verify(landmarkRepository).existsById(landmarkId);
        Mockito.verify(landmarkRepository).deleteById(landmarkId);
    }

    @Test
    public void deleteLandmark_notFound_throwsException() {
        Long landmarkId = 15L;

        Mockito.when(landmarkRepository.existsById(landmarkId)).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () ->
                landmarkService.deleteLandmark(landmarkId));

        Mockito.verify(landmarkRepository).existsById(landmarkId);
        Mockito.verify(landmarkRepository, Mockito.never()).deleteById(landmarkId);
    }

    @Test
    public void getNearbyLandmarks_validData_findNearbyLandmarkByRadiusAndLimit() {
        Double latitude = 43.3;
        Double longitude = 53.6;
        Integer radius = 300;
        Integer limit = 5;

        Long categoryId = (long) 5;
        LandmarkDto landmarkDto = initializeLandmarkDto(categoryId);
        LandmarkCategory landmarkCategory = initializeLandmarkCategory(categoryId);
        Landmark landmark = initializeLandmark(landmarkDto, landmarkCategory);

        Mockito.when(landmarkRepository.findNearbyLandmarkByRadiusAndLimit(
                latitude,
                longitude,
                radius,
                limit))
                        .thenReturn(List.of(landmark));

        List<LandmarkDto> result = landmarkService.getNearbyLandmarkOnRadiusAndLimit(
                latitude,
                longitude,
                radius,
                limit);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(landmark.getName(), result.get(0).name());
        Assertions.assertEquals(categoryId, result.get(0).landmarkCategoryId());

        Mockito.verify(landmarkRepository).findNearbyLandmarkByRadiusAndLimit(
                latitude,
                longitude,
                radius,
                limit);

    }

    private LandmarkCategory initializeLandmarkCategory(Long categoryId) {
        return LandmarkCategory.builder()
                .id(categoryId)
                .name("Архитектура")
                .build();
    }
    private LandmarkDto initializeLandmarkDto(Long categoryId) {
        return new LandmarkDto(
                null,
                "Пизанская башня",
                "POINT(53.25, 43.39)",
                "Стоит, падает, но не упадет",
                "Италия, Рим",
                categoryId,
                0.0
        );
    }

    private Landmark initializeLandmark(LandmarkDto landmarkDto, LandmarkCategory landmarkCategory) {
        return Landmark.builder().
                id((long) 15).
                name(landmarkDto.name()).
                coordinates(landmarkDto.coordinates()).
                description(landmarkDto.description()).
                address(landmarkDto.address()).
                category(landmarkCategory).
                build();
    }
}
