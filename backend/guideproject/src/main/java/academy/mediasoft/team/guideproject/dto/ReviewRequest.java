package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewRequest(
        @NotBlank(message = "Текст отзыва должен быть непустым")
        @Size(min = 30, max = 1000)
        String reviewText,
        Long landmarkId
) {}