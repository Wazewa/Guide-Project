package academy.mediasoft.team.guideproject.dto;

import java.time.LocalDateTime;

public record RatingDto(
        Long id,
        Double grade,
        LocalDateTime createdAt,
        Long personId,
        Long landmarkId
) { }
