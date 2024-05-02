package dev.matheuspereira.fluxcred.core.domain.ports.driver;

import dev.matheuspereira.fluxcred.core.domain.model.Person;

public interface IPersonService extends IBaseService<Person> {
  Person getByIdentifier(String identifier);
  Person save(Person person);
}
