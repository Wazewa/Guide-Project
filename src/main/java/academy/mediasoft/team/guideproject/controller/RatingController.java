package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.RatingDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.entity.Rating;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import academy.mediasoft.team.guideproject.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
public class RatingController {

    private final RatingRepository ratingRepository;
    private final PersonRepository personRepository;
    private final LandmarkRepository landmarkRepository;

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addRating(@RequestBody RatingDto ratingDto) {
        Landmark landmark = landmarkRepository.findById(ratingDto.landmarkId()).
                orElse(null);
        Person person = personRepository.findById(ratingDto.personId()).
                orElse(null);

        Rating rating = Rating.builder().
                grade(ratingDto.grade()).
                landmark(landmark).
                person(person).
                build();

        ratingRepository.save(rating);
    }

    @PutMapping("/{id}")
    public void updateRating(@PathVariable Long id,
                             @RequestBody RatingDto ratingDto) {
        Landmark landmark = landmarkRepository.findById(ratingDto.landmarkId()).
                orElse(null);
        Person person = personRepository.findById(ratingDto.personId()).
                orElse(null);

        Rating rating = Rating.builder().
                id(id).
                grade(ratingDto.grade()).
                landmark(landmark).
                person(person).
                build();

        if(ratingRepository.existsById(id)) {
            ratingRepository.save(rating);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable Long id) {
        if(ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
        }
    }
}
