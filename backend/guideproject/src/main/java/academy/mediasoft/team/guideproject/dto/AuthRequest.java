package academy.mediasoft.team.guideproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @Email
        @NotBlank(message = "Поле почты должно быть непустым")
        String email,
        @NotBlank(message = "Поле пароля должно быть непустым")
        String password
) { }
