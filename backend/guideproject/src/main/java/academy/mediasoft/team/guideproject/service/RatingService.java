package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.RatingDto;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.entity.Rating;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import academy.mediasoft.team.guideproject.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final PersonRepository personRepository;
    private final LandmarkRepository landmarkRepository;

    public List<RatingDto> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public RatingDto getRatingById(Long id) {
        return toDto(ratingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Оценка не найдена!")));
    }

    public RatingDto addRating(RatingDto ratingDto) {

        Person person = personRepository.findById(ratingDto.personId()).orElseThrow(
                () -> new RuntimeException("Пользователь не найден!")
        );

        Landmark landmark = landmarkRepository.findById(ratingDto.landmarkId()).orElseThrow(
                () -> new RuntimeException("Достопримечательность не найдена!")
        );

        Rating rating = Rating.builder().
                grade(ratingDto.grade()).
                person(person).
                landmark(landmark).
                build();

        return toDto(ratingRepository.save(rating));
    }

    public RatingDto updateRating(Long id, RatingDto ratingDto) {

        Rating existingRating = ratingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Оценка не найдена!")
        );

        Person person = personRepository.findById(ratingDto.personId()).orElseThrow(
                () -> new RuntimeException("Пользователь не найден!")
        );

        Landmark landmark = landmarkRepository.findById(ratingDto.landmarkId()).orElseThrow(
                () -> new RuntimeException("Достопримечательность не найдена!")
        );

        Rating rating = Rating.builder().
                id(id).
                grade(ratingDto.grade()).
                createdAt(existingRating.getCreatedAt()).
                person(person).
                landmark(landmark).
                build();

        return toDto(ratingRepository.save(rating));
    }

    public void deleteRating(Long id) {
        if(!ratingRepository.existsById(id)) {
            throw new RuntimeException("Оценка не найдена!");
        }
        ratingRepository.deleteById(id);
    }

    private RatingDto toDto(Rating rating) {
        return new RatingDto(
                rating.getId(),
                rating.getGrade(),
                rating.getCreatedAt(),
                rating.getLandmark().getId(),
                rating.getPerson().getId()
        );
    }
}
