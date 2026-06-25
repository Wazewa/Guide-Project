package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.ReviewDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.entity.Review;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import academy.mediasoft.team.guideproject.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PersonRepository personRepository;
    private final LandmarkRepository landmarkRepository;

    @Transactional(readOnly = true)
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReviewDto getReviewById(Long id) {
        return toDto(reviewRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Отзыв не существует!")));
    }

    @Transactional
    public ReviewDto addReview(ReviewDto reviewDto) {

        Person person = getPersonFromContext();

        Landmark landmark = landmarkRepository.findById(reviewDto.landmarkId()).orElseThrow(
                () -> new RuntimeException("Достопримечательность не найдена!")
        );

        if(reviewRepository.existsByPersonIdAndLandmarkId(person.getId(), reviewDto.landmarkId())) {
            throw new RuntimeException("Оценка уже есть!");
        }

        Review review = Review.builder().
                reviewText(reviewDto.reviewText()).
                person(person).
                landmark(landmark).
                build();

        return toDto(reviewRepository.save(review));
    }

    @Transactional
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {

        Review existingReview = reviewRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Отзыв не найден!")
        );

        Person person = getPersonFromContext();

        Landmark landmark = landmarkRepository.findById(reviewDto.landmarkId()).orElseThrow(
                () -> new RuntimeException("Достопримечательность не найдена!")
        );

        if(!existingReview.getPerson().getId().equals(person.getId())) {
            throw new RuntimeException("Нельзя написать отзыв от чужого имени!");
        }

        Review review = Review.builder().
                id(id).
                reviewText(reviewDto.reviewText()).
                person(person).
                createdAt(existingReview.getCreatedAt()).
                landmark(landmark).
                build();

        return toDto(reviewRepository.save(review));
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(()
                    -> new RuntimeException("Отзыв не найден!"));

        Person person = getPersonFromContext();

        if(!review.getPerson().getId().equals(person.getId())) {
            throw new RuntimeException("Удалять можно только свои отзывы");
        }

        reviewRepository.deleteById(id);
    }

    private ReviewDto toDto(Review review) {
        return new ReviewDto(
          review.getId(),
          review.getReviewText(),
          review.getCreatedAt(),
          review.getUpdatedAt(),
          review.getLandmark().getId()
        );
    }

    private Person getPersonFromContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        return personRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Пользователь не найден!")
        );
    }
}
