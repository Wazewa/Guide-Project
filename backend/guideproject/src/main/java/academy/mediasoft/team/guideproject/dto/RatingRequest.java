package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RatingRequest(
        Long id,
        @Min(1)
        @Max(5)
        @NotNull(message = "Оценка должна быть поставлена")
        Double grade,
        LocalDateTime createdAt,
        Long landmarkId)
{}