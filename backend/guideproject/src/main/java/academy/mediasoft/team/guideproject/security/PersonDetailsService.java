package academy.mediasoft.team.guideproject.security;

import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) {
        Person person =  personRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Пользователь не найден!")
        );

        return User.builder()
                .username(person.getEmail())
                .password(person.getHashPassword())
                .roles(person.getRole())
                .build();

    }
}
