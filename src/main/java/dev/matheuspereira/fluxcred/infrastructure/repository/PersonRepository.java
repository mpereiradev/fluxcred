package dev.matheuspereira.fluxcred.infrastructure.repository;

import dev.matheuspereira.fluxcred.domain.model.Person;
import dev.matheuspereira.fluxcred.domain.ports.driven.IPersonRepository;
import dev.matheuspereira.fluxcred.infrastructure.entity.PersonEntity;
import dev.matheuspereira.fluxcred.infrastructure.jpa.PersonJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
public class PersonRepository implements IPersonRepository {
  private final PersonJpaRepository personJpaRepository;
  private final ModelMapper modelMapper;


  @Override
  public Person save(Person person) {
    PersonEntity userEntity = personJpaRepository.save(modelMapper.map(person, PersonEntity.class));
    return modelMapper.map(userEntity, Person.class);
  }

  @Override
  public Optional<Person> findById(Integer id) {
    Optional<PersonEntity> personEntity = personJpaRepository.findById(id);
    return personEntity.map(p -> modelMapper.map(p, Person.class));
  }
  @Override
  public boolean existsById(Integer id) {
    return personJpaRepository.existsById(id);
  }

  @Override
  public Optional<Person> findByIdentifier(String identifier) {
    Optional<PersonEntity> personEntity = personJpaRepository.findByIdentifier(identifier);
    return personEntity.map(p -> modelMapper.map(p, Person.class));
  }

  @Override
  public void delete(Integer id) {
    try {
      personJpaRepository.deleteById(id);
    } catch (Exception e) {
      log.error("Person not deleted.", e);
      throw e;
    }
  }
}
