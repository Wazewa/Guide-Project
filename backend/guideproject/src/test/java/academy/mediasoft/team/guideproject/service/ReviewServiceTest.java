package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.ReviewDto;
import academy.mediasoft.team.guideproject.dto.ReviewRequest;
import academy.mediasoft.team.guideproject.dto.UpdateReviewRequest;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.LandmarkCategory;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.entity.Review;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import academy.mediasoft.team.guideproject.repository.ReviewRepository;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private LandmarkRepository landmarkRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void addReview_validData_save() {
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        ReviewDto reviewDto = initializeReviewDto();
        Review review = initializeReview(reviewDto, landmark, person);

        ReviewRequest request = new ReviewRequest(
                "Все прекрасно",
                landmark.getId()
        );

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(landmarkRepository.findById(reviewDto.landmarkId()))
                .thenReturn(Optional.of(landmark));
        Mockito.when(reviewRepository.existsByPersonIdAndLandmarkId(person.getId(), reviewDto.landmarkId()))
                .thenReturn(false);
        Mockito.when(reviewRepository.save(Mockito.any(Review.class)))
                .thenReturn(review);

        ReviewDto result = reviewService.addReview(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(review.getId(), result.id());
        Assertions.assertEquals(review.getCreatedAt(), result.createdAt());
        Assertions.assertEquals(review.getUpdatedAt(), result.updatedAt());
        Assertions.assertEquals(review.getReviewText(), result.reviewText());
        Assertions.assertEquals(review.getLandmark().getId(), result.landmarkId());

        Mockito.verify(landmarkRepository).findById(reviewDto.landmarkId());
        Mockito.verify(reviewRepository).existsByPersonIdAndLandmarkId(person.getId(), reviewDto.landmarkId());
        Mockito.verify(reviewRepository).save(Mockito.any(Review.class));
    }

    @Test
    public void addReview_landmarkNotFound_throwException() {
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        ReviewDto reviewDto = initializeReviewDto();

        ReviewRequest request = new ReviewRequest(
                "Все прекрасно",
                landmark.getId()
        );

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(landmarkRepository.findById(reviewDto.landmarkId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                reviewService.addReview(request));

        Mockito.verify(landmarkRepository).findById(reviewDto.landmarkId());
        Mockito.verify(reviewRepository, Mockito.never()).existsByPersonIdAndLandmarkId(person.getId(), reviewDto.landmarkId());
        Mockito.verify(reviewRepository, Mockito.never()).save(Mockito.any(Review.class));
    }

    @Test
    public void addReview_existingReview_throwException() {
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        ReviewDto reviewDto = initializeReviewDto();

        ReviewRequest request = new ReviewRequest(
                "Все прекрасно",
                landmark.getId()
        );

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(landmarkRepository.findById(reviewDto.landmarkId()))
                .thenReturn(Optional.of(landmark));
        Mockito.when(reviewRepository.existsByPersonIdAndLandmarkId(person.getId(), reviewDto.landmarkId()))
                .thenReturn(true);

        Assertions.assertThrows(RuntimeException.class, () ->
                reviewService.addReview(request));

        Mockito.verify(landmarkRepository).findById(reviewDto.landmarkId());
        Mockito.verify(reviewRepository).existsByPersonIdAndLandmarkId(person.getId(), reviewDto.landmarkId());
        Mockito.verify(reviewRepository, Mockito.never()).save(Mockito.any(Review.class));
    }

    @Test
    public void updateReview_validData_save() {
        Long reviewId = 1L;
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        ReviewDto reviewDto = initializeReviewDto();
        Review review = initializeReview(reviewDto, landmark, person);

        UpdateReviewRequest request = new UpdateReviewRequest("Все прекрасно");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(reviewRepository.findById(reviewId))
                .thenReturn(Optional.of(review));
        Mockito.when(reviewRepository.save(Mockito.any(Review.class)))
                .thenReturn(review);

        ReviewDto result = reviewService.updateReview(reviewId, request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(review.getId(), result.id());
        Assertions.assertEquals(review.getCreatedAt(), result.createdAt());
        Assertions.assertEquals(review.getReviewText(), result.reviewText());
        Assertions.assertEquals(review.getLandmark().getId(), result.landmarkId());

        Mockito.verify(reviewRepository).findById(reviewId);
        Mockito.verify(reviewRepository).save(Mockito.any(Review.class));
    }

    @Test
    public void updateReview_notReview_throwException() {
        Long reviewId = 1L;
        ReviewDto reviewDto = initializeReviewDto();

        UpdateReviewRequest request = new UpdateReviewRequest("Все прекрасно");

        Mockito.when(reviewRepository.findById(reviewId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                reviewService.updateReview(reviewId, request));

        Mockito.verify(reviewRepository).findById(reviewId);
        Mockito.verify(landmarkRepository, Mockito.never()).findById(reviewDto.landmarkId());
        Mockito.verify(reviewRepository, Mockito.never()).save(Mockito.any(Review.class));
    }

    @Test
    public void deleteReview_validData_delete() {
        Long reviewId = 1L;
        Person person = initializePerson();
        LandmarkCategory landmarkCategory = initializeLandmarkCategory();
        Landmark landmark = initializeLandmark(landmarkCategory);
        ReviewDto reviewDto = initializeReviewDto();
        Review review = initializeReview(reviewDto, landmark, person);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("wazewa@test.ru");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(personRepository.findByEmail("wazewa@test.ru"))
                .thenReturn(Optional.of(person));
        Mockito.when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        Mockito.doNothing().when(reviewRepository).deleteById(reviewId);

        reviewService.deleteReview(reviewId);

        Mockito.verify(reviewRepository).findById(reviewId);
        Mockito.verify(reviewRepository).deleteById(reviewId);
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

    private ReviewDto initializeReviewDto() {
        return new ReviewDto(
                1L,
                "Все прекрасно",
                LocalDateTime.now(),
                LocalDateTime.now(),
                15L,
        "Павел Симонов"
        );
    }

    private Review initializeReview(ReviewDto reviewDto,Landmark landmark, Person person) {
        return Review.builder().
                id(reviewDto.id()).
                reviewText(reviewDto.reviewText()).
                createdAt(reviewDto.createdAt()).
                updatedAt(reviewDto.updatedAt()).
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