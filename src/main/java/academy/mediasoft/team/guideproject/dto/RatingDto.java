package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record RatingDto(
        Long id,
        @Min(1)
        @Max(5)
        @NotBlank(message = "Оценка должна быть поставлена")
        Integer grade,
        LocalDateTime createdAt,
        Long personId,
        Long landmarkId
) { }
