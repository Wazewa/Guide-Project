package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.ReviewDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.entity.Review;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import academy.mediasoft.team.guideproject.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PersonRepository personRepository;
    private final LandmarkRepository landmarkRepository;

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public ReviewDto getReviewById(Long id) {
        return toDto(reviewRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Отзыв не существует!")));
    }

    public ReviewDto addReview(ReviewDto reviewDto) {

        Person person = personRepository.findById(reviewDto.personId()).orElseThrow(
                () -> new RuntimeException("Пользователь не найден!")
        );

        Landmark landmark = landmarkRepository.findById(reviewDto.landmarkId()).orElseThrow(
                () -> new RuntimeException("Достопримечательность не найдена!")
        );

        Review review = Review.builder().
                reviewText(reviewDto.reviewText()).
                person(person).
                landmark(landmark).
                build();

        return toDto(reviewRepository.save(review));
    }

    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {

        Review existingReview = reviewRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Отзыв не найден!")
        );

        Person person = personRepository.findById(reviewDto.personId()).orElseThrow(
                () -> new RuntimeException("Пользователь не найден!")
        );

        Landmark landmark = landmarkRepository.findById(reviewDto.landmarkId()).orElseThrow(
                () -> new RuntimeException("Достопримечательность не найдена!")
        );

        Review review = Review.builder().
                id(id).
                reviewText(reviewDto.reviewText()).
                person(person).
                createdAt(existingReview.getCreatedAt()).
                landmark(landmark).
                build();

        return toDto(reviewRepository.save(review));
    }

    public void deleteReview(Long id) {
        if(!reviewRepository.existsById(id)) {
            throw new RuntimeException("Отзыв не найден!");
        }
        reviewRepository.deleteById(id);
    }

    private ReviewDto toDto(Review review) {
        return new ReviewDto(
          review.getId(),
          review.getReviewText(),
          review.getCreatedAt(),
          review.getUpdatedAt(),
          review.getPerson().getId(),
          review.getLandmark().getId()
        );
    }
}
