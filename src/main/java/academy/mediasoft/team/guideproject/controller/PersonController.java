package academy.mediasoft.team.guideproject.controller;

import academy.mediasoft.team.guideproject.dto.PersonDto;
import academy.mediasoft.team.guideproject.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<PersonDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {

        PersonDto personDto = personService.getPersonById(id);

        return ResponseEntity.status(HttpStatus.OK).body(personDto);
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@RequestBody @Valid PersonDto personDto) {

        PersonDto createdPerson = personService.addPerson(personDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Long id,
                             @RequestBody @Valid PersonDto personDto) {

        PersonDto updatedPerson = personService.updatePerson(id, personDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);

        return ResponseEntity.noContent().build();
    }
}
