package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.ReviewDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.entity.Review;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import academy.mediasoft.team.guideproject.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final PersonRepository personRepository;
    private final LandmarkRepository landmarkRepository;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addReview(@RequestBody ReviewDto reviewDto) {
        Person person = personRepository.findById(reviewDto.personId())
                .orElse(null);
        Landmark landmark = landmarkRepository.findById(reviewDto.landmarkId())
                .orElse(null);

        Review review = Review.builder().
                reviewText(reviewDto.reviewText()).
                person(person).
                landmark(landmark).
                build();

        reviewRepository.save(review);
    }

    @PutMapping("/{id}")
    public void updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        Person person = personRepository.findById(reviewDto.personId())
                .orElse(null);
        Landmark landmark = landmarkRepository.findById(reviewDto.landmarkId())
                .orElse(null);

        Review review = Review.builder().
                id(id).
                reviewText(reviewDto.reviewText()).
                person(person).
                landmark(landmark).
                build();

        if(reviewRepository.existsById(id)) {
            reviewRepository.save(review);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        if(reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        }
    }
}
