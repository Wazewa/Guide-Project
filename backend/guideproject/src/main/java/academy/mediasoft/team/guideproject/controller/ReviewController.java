package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.ReviewDto;
import academy.mediasoft.team.guideproject.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {

        ReviewDto reviewDto = reviewService.getReviewById(id);

        return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody @Valid ReviewDto reviewDto) {

        ReviewDto createdReview = reviewService.addReview(reviewDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto>  updateReview(@PathVariable Long id,
                             @RequestBody @Valid ReviewDto reviewDto) {

        ReviewDto updatedReview = reviewService.updateReview(id, reviewDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {

        reviewService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }
}
