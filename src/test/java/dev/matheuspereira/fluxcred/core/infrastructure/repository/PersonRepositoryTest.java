package dev.matheuspereira.fluxcred.core.infrastructure.repository;

import dev.matheuspereira.fluxcred.core.domain.model.Person;
import dev.matheuspereira.fluxcred.core.infrastructure.entity.PersonEntity;
import dev.matheuspereira.fluxcred.core.infrastructure.jpa.PersonJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

  @Mock
  private PersonJpaRepository personJpaRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private PersonRepository personRepository;

  @Test
  void testSave() {
    Person person = new Person();
    PersonEntity personEntity = new PersonEntity();
    when(modelMapper.map(any(Person.class), eq(PersonEntity.class))).thenReturn(personEntity);
    when(personJpaRepository.save(any(PersonEntity.class))).thenReturn(personEntity);
    when(modelMapper.map(any(PersonEntity.class), eq(Person.class))).thenReturn(person);

    personRepository.save(person);

    verify(personJpaRepository).save(personEntity);
    verify(modelMapper).map(person, PersonEntity.class);
    verify(modelMapper).map(personEntity, Person.class);
  }

  @Test
  void testSaveThrowsException() {
    Person person = new Person();
    when(modelMapper.map(any(Person.class), eq(PersonEntity.class))).thenThrow(new RuntimeException("Mapping failure"));

    assertThrows(RuntimeException.class, () -> personRepository.save(person));
    verify(personJpaRepository, never()).save(any(PersonEntity.class));
  }

  @Test
  void testFindById() {
    Integer id = 1;
    Person person = new Person();
    Optional<PersonEntity> personEntity = Optional.of(new PersonEntity());
    when(personJpaRepository.findById(id)).thenReturn(personEntity);
    when(modelMapper.map(any(PersonEntity.class), eq(Person.class))).thenReturn(person);

    Optional<Person> result = personRepository.findById(id);

    verify(personJpaRepository).findById(id);
    verify(modelMapper).map(personEntity.get(), Person.class);
  }

  @Test
  void testFindByIdWhenEmpty() {
    Integer id = 1;
    when(personJpaRepository.findById(id)).thenReturn(Optional.empty());

    Optional<Person> result = personRepository.findById(id);

    assertTrue(result.isEmpty());
    verify(personJpaRepository).findById(id);
    verify(modelMapper, never()).map(any(), eq(Person.class));
  }

  @Test
  void testExistsById() {
    Integer id = 1;
    when(personJpaRepository.existsById(id)).thenReturn(true);

    boolean exists = personRepository.existsById(id);

    verify(personJpaRepository).existsById(id);
    assertTrue(exists);
  }

  @Test
  void testExistsByIdWhenNotExists() {
    Integer id = 1;
    when(personJpaRepository.existsById(id)).thenReturn(false);

    boolean exists = personRepository.existsById(id);

    verify(personJpaRepository).existsById(id);
    assertFalse(exists);
  }

  @Test
  void testDelete() {
    Integer id = 1;

    personRepository.delete(id);

    verify(personJpaRepository).deleteById(id);
  }

  @Test
  void testDeleteThrowsException() {
    doThrow(new RuntimeException("Delete failure")).when(personJpaRepository).deleteById(any());

    Integer id = 1;
    Exception exception = assertThrows(RuntimeException.class, () -> personRepository.delete(id));
    assertEquals("Delete failure", exception.getMessage());
    verify(personJpaRepository).deleteById(id);
  }

  @Test
  void testFindByIdentifier() {
    String identifier = "123";
    Person person = new Person();
    Optional<PersonEntity> personEntity = Optional.of(new PersonEntity());
    when(personJpaRepository.findByIdentifier(identifier)).thenReturn(personEntity);
    when(modelMapper.map(any(PersonEntity.class), eq(Person.class))).thenReturn(person);

    Optional<Person> result = personRepository.findByIdentifier(identifier);

    verify(personJpaRepository).findByIdentifier(identifier);
    verify(modelMapper).map(personEntity.get(), Person.class);
  }

  @Test
  void testFindByIdentifierWhenEmpty() {
    String identifier = "123";
    when(personJpaRepository.findByIdentifier(identifier)).thenReturn(Optional.empty());

    Optional<Person> result = personRepository.findByIdentifier(identifier);

    assertTrue(result.isEmpty());
    verify(personJpaRepository).findByIdentifier(identifier);
    verify(modelMapper, never()).map(any(), eq(Person.class));
  }
}
