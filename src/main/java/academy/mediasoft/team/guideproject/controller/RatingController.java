package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.RatingDto;
import academy.mediasoft.team.guideproject.service.RatingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public List<RatingDto> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDto> getRatingById(@PathVariable Long id) {

        RatingDto ratingDto = ratingService.getRatingById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ratingDto);
    }

    @PostMapping
    public ResponseEntity<RatingDto> addRating(@RequestBody @Valid RatingDto ratingDto) {

        RatingDto createdRating = ratingService.addRating(ratingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Long id,
                                                   @RequestBody @Valid RatingDto ratingDto) {

        RatingDto updatedRating = ratingService.updateRating(id, ratingDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedRating);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {

        ratingService.deleteRating(id);

        return ResponseEntity.noContent().build();
    }
}
