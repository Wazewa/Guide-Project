package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PersonDto (
    Long id,
    @NotBlank(message = "Поле имени должно быть непустым")
    @Size(min = 2, max = 40, message = "Имя должно быть от 2 букв")
    String name,
    @NotBlank(message = "Поле фамилии должно быть непустым")
    @Size(min = 2, max = 40, message = "Фамилия должна быть от 2 букв")
    String surname,
    @Email
    @NotBlank(message = "Поле почты должно быть непустым")
    String email,
    @NotBlank(message = "Поле роли должно быть непустым")
    String role
) {}