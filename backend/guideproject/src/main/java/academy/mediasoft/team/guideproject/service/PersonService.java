package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.PersonDto;
import academy.mediasoft.team.guideproject.dto.RegisterDto;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<PersonDto> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PersonDto getPersonById(Long id) {
        return toDto(personRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Пользователь не найден!")));
    }

    @Transactional
    public PersonDto addPerson(RegisterDto registerDto) {
        if(personRepository.existsByEmail(registerDto.email())) {
            throw new RuntimeException("Пользователь с такой почтой уже есть!");
        }

        Person person = Person.builder().
                name(registerDto.name()).
                surname(registerDto.surname()).
                email(registerDto.email()).
                hashPassword(passwordEncoder.encode(registerDto.password())).
                role("USER").
                build();
        Person createdPerson = personRepository.save(person);

        return toDto(createdPerson);
    }

    @Transactional
    public PersonDto updatePerson(Long id, PersonDto personDto) {
        Person existingPerson = personRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Пользователь не найден!"));

        Person person = Person.builder().
                id(id).
                name(personDto.name()).
                surname(personDto.surname()).
                email(personDto.email()).
                hashPassword(existingPerson.getHashPassword()).
                build();

        return toDto(personRepository.save(person));
    }

    @Transactional
    public void deletePerson(Long id) {
        if(!personRepository.existsById(id)) {
            throw new RuntimeException("Пользователь не найден!");
        }
        personRepository.deleteById(id);
    }


    private PersonDto toDto(Person person) {
        return new PersonDto(
                person.getId(),
                person.getName(),
                person.getSurname(),
                person.getEmail()
        );
    }
}
