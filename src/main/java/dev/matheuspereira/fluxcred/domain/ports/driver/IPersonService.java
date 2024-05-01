package dev.matheuspereira.fluxcred.domain.ports.driver;

import dev.matheuspereira.fluxcred.domain.model.Person;

public interface IPersonService extends IBaseService<Person> {
  Person getByIdentifier(String identifier);
  Person save(Person person);
}
