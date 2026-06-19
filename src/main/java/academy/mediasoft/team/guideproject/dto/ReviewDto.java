package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ReviewDto(
        Long id,
        @NotBlank(message = "Текст отзыва должен быть непустым")
        @Size(min = 30, max = 1000)
        String reviewText,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long personId,
        Long landmarkId
) { }
