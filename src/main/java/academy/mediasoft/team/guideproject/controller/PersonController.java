package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.PersonDto;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonRepository personRepository;
    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personRepository.findById(id).orElse(null);
    }
    @PostMapping
    public void addPerson(@RequestBody PersonDto personDto) {
        Person person = Person.builder().
                name(personDto.name()).
                surname(personDto.surname()).
                email(personDto.email()).
                hashPassword("123456").
                build();
        personRepository.save(person);
    }
    @PutMapping("/{id}")
    public void updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        Person person = Person.builder().
                id(id).
                name(personDto.name()).
                surname(personDto.surname()).
                email(personDto.email()).
                hashPassword("123456").
                build();
        if(personRepository.existsById(id)) {
            personRepository.save(person);
        }
    }
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
}
