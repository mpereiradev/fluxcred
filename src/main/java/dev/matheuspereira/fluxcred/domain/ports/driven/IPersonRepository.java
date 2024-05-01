package dev.matheuspereira.fluxcred.domain.ports.driven;

import dev.matheuspereira.fluxcred.domain.model.Person;

import java.util.Optional;

public interface IPersonRepository extends IBaseRepository<Person> {
  Optional<Person> findByIdentifier(String identifier);
}
