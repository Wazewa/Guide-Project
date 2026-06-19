package academy.mediasoft.team.guideproject.dto;

import java.time.LocalDateTime;

public record ReviewDto(
        Long id,
        String reviewText,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long personId,
        Long landmarkId) { }
