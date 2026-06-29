package academy.mediasoft.team.guideproject.controller;


import academy.mediasoft.team.guideproject.dto.AuthRequest;
import academy.mediasoft.team.guideproject.dto.AuthResponse;
import academy.mediasoft.team.guideproject.dto.PersonDto;
import academy.mediasoft.team.guideproject.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PersonService personService;

    @GetMapping("/me")
    public PersonDto getCurrentUser(Authentication authentication) {
        return personService.getPersonByEmail(authentication.getName());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
        );

        HttpSession session = request.getSession(true);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);

        return new AuthResponse("Аутентификация прошла успешно");
    }
}
