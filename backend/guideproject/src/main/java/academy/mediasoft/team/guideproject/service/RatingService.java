package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.RatingDto;
import academy.mediasoft.team.guideproject.dto.RatingRequest;
import academy.mediasoft.team.guideproject.entity.Landmark;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.entity.Rating;
import academy.mediasoft.team.guideproject.repository.LandmarkRepository;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import academy.mediasoft.team.guideproject.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final PersonRepository personRepository;
    private final LandmarkRepository landmarkRepository;

    @Transactional(readOnly = true)
    public List<RatingDto> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RatingDto getRatingById(Long id) {
        return toDto(ratingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Оценка не найдена!")));
    }

    @Transactional(readOnly = true)
    public List<RatingDto> getRatingsByLandmarkId(Long landmarkId) {
        return ratingRepository.findByLandmarkId(landmarkId).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public RatingDto addRating(RatingRequest request) {

        Person person = getPersonFromContext();

        Landmark landmark = landmarkRepository.findById(request.landmarkId()).orElseThrow(
                () -> new RuntimeException("Достопримечательность не найдена!")
        );

        if(ratingRepository.existsByPersonIdAndLandmarkId(person.getId(), request.landmarkId())) {
            throw new RuntimeException("Оценка уже есть!");
        }

        Rating rating = Rating.builder().
                grade(request.grade()).
                person(person).
                landmark(landmark).
                build();

        return toDto(ratingRepository.save(rating));
    }

    @Transactional
    public RatingDto updateRating(Long id, RatingRequest request) {

        Rating existingRating = ratingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Оценка не найдена!")
        );

        Person person = getPersonFromContext();

        Landmark landmark = landmarkRepository.findById(request.landmarkId()).orElseThrow(
                () -> new RuntimeException("Достопримечательность не найдена!")
        );

        if(!existingRating.getPerson().getId().equals(person.getId())) {
            throw new RuntimeException("Оценка ставится лишь на свои отзывы!");
        }

        Rating rating = Rating.builder().
                id(id).
                grade(request.grade()).
                createdAt(existingRating.getCreatedAt()).
                person(person).
                landmark(landmark).
                build();

        return toDto(ratingRepository.save(rating));
    }

    @Transactional
    public void deleteRating(Long id) {
        Rating rating = ratingRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Оценка не найдена!"));

        Person person = getPersonFromContext();

        if(!rating.getPerson().getId().equals(person.getId())) {
            throw new RuntimeException("Удалять можно только свои оценки!");
        }

        ratingRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Double getAverageRatingForLandmark(Long id) {
        return ratingRepository.getAverageRatingById(id);
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

    private Person getPersonFromContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        return personRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Пользователь не найден!")
        );
    }


}
