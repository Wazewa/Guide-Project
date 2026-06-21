package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LandmarkDto(
    Long id,
    @NotBlank(message = "Поле имени достопримечательности должно быть непустым")
    @Size(min = 2, max = 64, message = "Название достопримечательности должно быть от 2 букв")
    String name,
    @NotBlank(message = "Поле координат достопримечательности должно быть непустым")
    @Size(min = 2, max = 128)
    String coordinates,
    @NotBlank(message = "Поле описания достопримечательности должно быть непустым")
    @Size(min = 32, max = 1024)
    String description,
    @NotBlank(message = "Поле адреса достопримечательности должно быть непустым")
    @Size(min = 2, max = 64)
    String address,
    Long landmarkCategoryId,
    Double averageRating
) {}
