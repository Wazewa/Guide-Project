package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.PersonDto;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<PersonDto> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public PersonDto getPersonById(Long id) {
        return toDto(personRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Пользователь не найден!")));
    }

    public PersonDto addPerson(PersonDto personDto) {
        Person person = Person.builder().
                name(personDto.name()).
                surname(personDto.surname()).
                email(personDto.email()).
                hashPassword("123456").
                build();
        Person createdPerson = personRepository.save(person);

        return toDto(createdPerson);
    }

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

    public void deletePerson(Long id) {
        if(personRepository.existsById(id)) {
            personRepository.deleteById(id);
        }
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
