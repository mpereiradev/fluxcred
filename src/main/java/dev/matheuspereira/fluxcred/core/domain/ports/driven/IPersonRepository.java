package dev.matheuspereira.fluxcred.core.domain.ports.driven;

import dev.matheuspereira.fluxcred.core.domain.model.Person;

import java.util.Optional;

public interface IPersonRepository extends IBaseRepository<Person> {
  Optional<Person> findByIdentifier(String identifier);
}
