package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LandmarkCategoryDto(
        Long id,
        @NotBlank(message = "Поле категории достопримечательности должно быть непустым")
        @Size(min = 4, max = 64, message = "Категория достопримечательности должна быть от 4 букв")
        String name
) {}
