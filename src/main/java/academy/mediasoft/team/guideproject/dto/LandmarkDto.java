package academy.mediasoft.team.guideproject.dto;

public record LandmarkDto(
    Long id,
    String name,
    String coordinates,
    String description,
    String address,
    Long landmarkCategoryId
) {}
