package dev.matheuspereira.fluxcred.domain.service;

import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.domain.model.LoanConfig;
import dev.matheuspereira.fluxcred.domain.model.Person;
import dev.matheuspereira.fluxcred.domain.ports.driven.IPersonRepository;
import dev.matheuspereira.fluxcred.domain.ports.driver.ILoanConfigService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  @Mock
  private IPersonRepository personRepository;

  @Mock
  private ILoanConfigService loanConfigService;

  @InjectMocks
  private PersonService personService;

  @Test
  void createShouldThrowWhenIdentifierExists() {
    Person person = new Person();
    person.setIdentifier("12345678");
    when(personRepository.findByIdentifier(any())).thenReturn(Optional.of(new Person()));

    assertThrows(BusinessException.class, () -> personService.create(person));
  }

  @Test
  void createShouldSavePerson() {
    Person person = new Person();
    person.setIdentifier("12345678");
    when(personRepository.findByIdentifier(any())).thenReturn(Optional.empty());
    when(loanConfigService.getByIdentifierType(any())).thenReturn(new LoanConfig());
    when(personRepository.save(any())).thenReturn(person);

    Person savedPerson = personService.create(person);

    assertNotNull(savedPerson);
    verify(personRepository).save(person);
  }

  @Test
  void createPersonWithInvalidCPFShouldThrowBusinessException() {
    Person person = new Person();
    person.setIdentifier("11111111111");

    BusinessException thrown = assertThrows(
        BusinessException.class,
        () -> personService.create(person),
        "Expected create() to throw, but it did not"
    );

    assertTrue(thrown.getMessage().contains("The identifier is invalid for type"));
  }

  @Test
  void getByIdShouldReturnPerson() {
    Person person = new Person();
    when(personRepository.findById(any())).thenReturn(Optional.of(person));

    Person foundPerson = personService.getById(1);

    assertEquals(person, foundPerson);
  }

  @Test
  void getByIdShouldThrowNotFoundException() {
    when(personRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> personService.getById(1));
  }

  @Test
  void getByIdentifierShouldReturnPerson() {
    Person person = new Person();
    when(personRepository.findByIdentifier(any())).thenReturn(Optional.of(person));

    Person foundPerson = personService.getByIdentifier("12345678");

    assertEquals(person, foundPerson);
  }

  @Test
  void getByIdentifierShouldThrowNotFoundException() {
    when(personRepository.findByIdentifier(any())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> personService.getByIdentifier("123"));
  }

  @Test
  void updateShouldThrowIfPersonNotFound() {
    when(personRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(BusinessException.class, () -> personService.update(1, new Person()));
  }

  @Test
  void updateShouldThrowIfIdentifierExists() {
    Person existingPerson = new Person();
    Person personPatch = new Person();
    existingPerson.setIdentifier("12345678");
    personPatch.setIdentifier("12345678");
    when(personRepository.findById(any())).thenReturn(Optional.of(personPatch));
    when(personRepository.findByIdentifier(any())).thenReturn(Optional.of(existingPerson));

    assertThrows(BusinessException.class, () -> personService.update(1, personPatch));
  }

  @Test
  void updateShouldSavePerson() {
    Person existingPerson = new Person();
    existingPerson.setIdentifier("12345678");
    when(personRepository.findById(any())).thenReturn(Optional.of(existingPerson));
    when(personRepository.findByIdentifier(any())).thenReturn(Optional.empty());
    when(personRepository.save(any())).thenReturn(existingPerson);

    Person updatedPerson = personService.update(1, existingPerson);

    assertEquals(existingPerson, updatedPerson);
    verify(personRepository).save(existingPerson);
  }

  @Test
  void deleteShouldCallRepositoryDelete() {
    personService.delete(1);

    verify(personRepository).delete(1);
  }
}
