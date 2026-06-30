package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateReviewRequest(
        @NotBlank(message = "Текст отзыва не может быть пустым")
        String reviewText
) {}