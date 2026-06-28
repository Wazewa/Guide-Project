package academy.mediasoft.team.guideproject.service;

import academy.mediasoft.team.guideproject.dto.PersonDto;
import academy.mediasoft.team.guideproject.dto.RegisterDto;
import academy.mediasoft.team.guideproject.entity.Person;
import academy.mediasoft.team.guideproject.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PersonService personService;

    @Test
    public void addPerson_validData_save() {

        String password = "123456";
        String hashPassword = "$2a$10$superEncodedPassword123";

        RegisterDto registerDto = initializeRegisterUser(password);
        PersonDto personDto = initializePersonDto(registerDto);
        Person person = initializePerson(personDto, hashPassword);

        Mockito.when(personRepository.existsByEmail(registerDto.email())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(password))
                .thenReturn(hashPassword);
        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        PersonDto result = personService.addPerson(registerDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(person.getId(), result.id());
        Assertions.assertEquals(person.getName(), result.name());
        Assertions.assertEquals(person.getSurname(), result.surname());
        Assertions.assertEquals(person.getEmail(), result.email());
        Assertions.assertEquals(hashPassword, person.getHashPassword());
        Assertions.assertEquals(person.getRole(), result.role());

        Mockito.verify(personRepository).existsByEmail(registerDto.email());
        Mockito.verify(passwordEncoder).encode(registerDto.password());
        Mockito.verify(personRepository).save(Mockito.any(Person.class));
    }

    @Test
    public void addPerson_duplicateEmail_throwException() {

        String password = "123456";
        String hashPassword = "$2a$10$superEncodedPassword123";

        RegisterDto registerDto = initializeRegisterUser(password);
        PersonDto personDto = initializePersonDto(registerDto);
        Person person = initializePerson(personDto, hashPassword);

        Mockito.when(personRepository.existsByEmail(registerDto.email())).thenReturn(true);

        Assertions.assertThrows(RuntimeException.class, () ->
                personService.addPerson(registerDto));


        Mockito.verify(personRepository).existsByEmail(person.getEmail());
        Mockito.verify(passwordEncoder, Mockito.never()).encode(registerDto.password());
        Mockito.verify(personRepository, Mockito.never()).save(Mockito.any(Person.class));

    }

    @Test
    public void updatePerson_validData_save() {

        Long personId = 15L;
        String password = "123456";
        String hashPassword = "$2a$10$superEncodedPassword123";

        RegisterDto registerDto = initializeRegisterUser(password);
        PersonDto personDto = initializePersonDto(registerDto);
        Person person = initializePerson(personDto, hashPassword);

        Mockito.when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        PersonDto result = personService.updatePerson(personId, personDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(person.getId(), result.id());
        Assertions.assertEquals(person.getName(), result.name());
        Assertions.assertEquals(person.getSurname(), result.surname());
        Assertions.assertEquals(person.getEmail(), result.email());
        Assertions.assertEquals(hashPassword, person.getHashPassword());
        Assertions.assertEquals(person.getRole(), result.role());

        Mockito.verify(personRepository).findById(personId);
        Mockito.verify(personRepository).save(Mockito.any(Person.class));
    }

    @Test
    public void updatePerson_personNotFound_throwException() {

        Long personId = 15L;
        String password = "123456";
        String hashPassword = "$2a$10$superEncodedPassword123";

        RegisterDto registerDto = initializeRegisterUser(password);
        PersonDto personDto = initializePersonDto(registerDto);
        Person person = initializePerson(personDto, hashPassword);

        Mockito.when(personRepository.findById(personId)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                personService.updatePerson(personId, personDto));

        Mockito.verify(personRepository).findById(personId);
        Mockito.verify(personRepository, Mockito.never()).save(person);
    }

    @Test
    public void deletePerson_validData_delete() {
        Long personId = 15L;

        Mockito.when(personRepository.existsById(personId)).thenReturn(true);
        Mockito.doNothing().when(personRepository).deleteById(personId);

        personService.deletePerson(personId);

        Mockito.verify(personRepository).existsById(personId);
        Mockito.verify(personRepository).deleteById(personId);
    }

    @Test
    public void deletePerson_notFoundPerson_delete() {
        Long personId = 15L;

        Mockito.when(personRepository.existsById(personId)).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () ->
                personService.deletePerson(personId));

        Mockito.verify(personRepository).existsById(personId);
        Mockito.verify(personRepository, Mockito.never()).deleteById(personId);
    }

    private PersonDto initializePersonDto(RegisterDto registerDto) {
        return new PersonDto(
                15L,
                registerDto.name(),
                        registerDto.surname(),
                        registerDto.email(),
                registerDto.role()
        );
    }

    private RegisterDto initializeRegisterUser(String password) {
        return new RegisterDto(
                "Иван",
                "Королев",
                "wazewa73@test.ru",
                password,
                "USER"
        );
    }

    private Person initializePerson(PersonDto personDto, String hashPassword) {
        return Person.builder().
                id(15L).
                name(personDto.name()).
                surname(personDto.surname()).
                email(personDto.email()).
                hashPassword(hashPassword).
                role("USER").
                build();
    }
}
