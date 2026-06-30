package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.RatingDto;
import academy.mediasoft.team.guideproject.dto.RatingRequest;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.entity.Rating;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import academy.mediasoft.team.guideproject.repository.RatingRepository;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    RatingRepository ratingRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private LandmarkRepository landmarkRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private RatingService ratingService;

    @Test
    public void addRating_validData_save() {
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        RatingDto ratingDto = initializeRatingDto();
        Rating rating = initializeRating(ratingDto, landmark, person);

        RatingRequest request = new RatingRequest(
                1L,
                (double) 5,
                LocalDateTime.now(),
                1L
        );

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(landmarkRepository.findById(ratingDto.landmarkId()))
                .thenReturn(Optional.of(landmark));
        Mockito.when(ratingRepository.existsByPersonIdAndLandmarkId(person.getId(), ratingDto.landmarkId()))
                .thenReturn(false);
        Mockito.when(ratingRepository.save(Mockito.any(Rating.class)))
                .thenReturn(rating);

        RatingDto result = ratingService.addRating(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(rating.getId(), result.id());
        Assertions.assertEquals(rating.getCreatedAt(), result.createdAt());
        Assertions.assertEquals(rating.getGrade(), result.grade());
        Assertions.assertEquals(rating.getLandmark().getId(), result.landmarkId());

        Mockito.verify(landmarkRepository).findById(ratingDto.landmarkId());
        Mockito.verify(ratingRepository).existsByPersonIdAndLandmarkId(person.getId(), ratingDto.landmarkId());
        Mockito.verify(ratingRepository).save(Mockito.any(Rating.class));
    }

    @Test
    public void addRating_landmarkNotFound_throwException() {
        Person person = initializePerson();
        RatingDto ratingDto = initializeRatingDto();

        RatingRequest request = new RatingRequest(
                1L,
                (double) 5,
                LocalDateTime.now(),
                1L
        );

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(landmarkRepository.findById(ratingDto.landmarkId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                ratingService.addRating(request));

        Mockito.verify(landmarkRepository).findById(ratingDto.landmarkId());
        Mockito.verify(ratingRepository, Mockito.never()).existsByPersonIdAndLandmarkId(person.getId(), ratingDto.landmarkId());
        Mockito.verify(ratingRepository, Mockito.never()).save(Mockito.any(Rating.class));
    }

    @Test
    public void addRating_existingRating_throwException() {
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        RatingDto ratingDto = initializeRatingDto();

        RatingRequest request = new RatingRequest(
                1L,
                (double) 5,
                LocalDateTime.now(),
                1L
        );

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(landmarkRepository.findById(ratingDto.landmarkId()))
                .thenReturn(Optional.of(landmark));
        Mockito.when(ratingRepository.existsByPersonIdAndLandmarkId(person.getId(), ratingDto.landmarkId()))
                .thenReturn(true);

        Assertions.assertThrows(RuntimeException.class, () ->
                ratingService.addRating(request));

        Mockito.verify(landmarkRepository).findById(ratingDto.landmarkId());
        Mockito.verify(ratingRepository).existsByPersonIdAndLandmarkId(person.getId(), ratingDto.landmarkId());
        Mockito.verify(ratingRepository, Mockito.never()).save(Mockito.any(Rating.class));
    }

    @Test
    public void updateRating_validData_save() {
        Long ratingId = 1L;
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        RatingDto ratingDto = initializeRatingDto();
        Rating rating = initializeRating(ratingDto, landmark, person);

        RatingRequest request = new RatingRequest(
                1L,
                (double) 5,
                LocalDateTime.now(),
                1L
        );

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(ratingRepository.findById(ratingId))
                .thenReturn(Optional.of(rating));
        Mockito.when(landmarkRepository.findById(ratingDto.landmarkId()))
                .thenReturn(Optional.of(landmark));
        Mockito.when(ratingRepository.save(Mockito.any(Rating.class)))
                .thenReturn(rating);

        RatingDto result = ratingService.updateRating(ratingId, request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(rating.getId(), result.id());
        Assertions.assertEquals(rating.getCreatedAt(), result.createdAt());
        Assertions.assertEquals(rating.getGrade(), result.grade());
        Assertions.assertEquals(rating.getLandmark().getId(), result.landmarkId());

        Mockito.verify(ratingRepository).findById(ratingId);
        Mockito.verify(landmarkRepository).findById(ratingDto.landmarkId());
        Mockito.verify(ratingRepository).save(Mockito.any(Rating.class));
    }

    @Test
    public void updateRating_notRating_throwException() {
        Long ratingId = 1L;
        RatingDto ratingDto = initializeRatingDto();

        RatingRequest request = new RatingRequest(
                1L,
                (double) 5,
                LocalDateTime.now(),
                1L
        );

        Mockito.when(ratingRepository.findById(ratingId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                ratingService.updateRating(ratingId, request));

        Mockito.verify(ratingRepository).findById(ratingId);
        Mockito.verify(landmarkRepository, Mockito.never()).findById(ratingDto.landmarkId());
        Mockito.verify(ratingRepository, Mockito.never()).save(Mockito.any(Rating.class));
    }

    @Test
    public void updateRating_notLandmark_throwException() {
        Long ratingId = 1L;
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        RatingDto ratingDto = initializeRatingDto();
        Rating rating = initializeRating(ratingDto, landmark, person);

        RatingRequest request = new RatingRequest(
                1L,
                (double) 5,
                LocalDateTime.now(),
                1L
        );

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(ratingRepository.findById(ratingId))
                .thenReturn(Optional.of(rating));
        Mockito.when(landmarkRepository.findById(ratingDto.landmarkId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                ratingService.updateRating(ratingId, request));

        Mockito.verify(ratingRepository).findById(ratingId);
        Mockito.verify(landmarkRepository).findById(ratingDto.landmarkId());
        Mockito.verify(ratingRepository, Mockito.never()).save(Mockito.any(Rating.class));
    }

    @Test
    public void deleteRating_validData_delete() {
        Long ratingId = 1L;
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        RatingDto ratingDto = initializeRatingDto();
        Rating rating = initializeRating(ratingDto, landmark, person);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        Mockito.doNothing().when(ratingRepository).deleteById(ratingId);

        ratingService.deleteRating(ratingId);

        Mockito.verify(ratingRepository).findById(ratingId);
        Mockito.verify(ratingRepository).deleteById(ratingId);
    }

    private Person initializePerson() {
        return Person.builder().
                id(15L).
                name("Иван").
                surname("Королев").
                email("wazewa@test.ru").
                hashPassword("$2@atrnglksuperHashPassword12348756").
                role("USER").
                build();
    }

    private RatingDto initializeRatingDto() {
        return new RatingDto(
                1L,
                (double) 5,
                LocalDateTime.now(),
                1L,
                15L
        );
    }

    private Rating initializeRating(RatingDto ratingDto,Landmark landmark, Person person) {
        return Rating.builder().
                id(ratingDto.id()).
                grade(ratingDto.grade()).
                createdAt(ratingDto.createdAt()).
                landmark(landmark).
                person(person).
                build();
    }

    private Landmark initializeLandmark(LandmarkCategory landmarkCategory) {
        return Landmark.builder().
                id(15L).
                name("Пизанская башня").
                coordinates("POINT(53.25, 43.39)").
                description("Стоит, падает, но не упадет").
                address("Италия, Рим").
                category(landmarkCategory)
        .build();
    }

    private LandmarkCategory initializeLandmarkCategory() {
        return LandmarkCategory.builder()
                .id(5L)
                .name("Архитектура")
                .build();
    }
}
