package dev.matheuspereira.fluxcred.domain.service;

import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.domain.model.IdentifierType;
import dev.matheuspereira.fluxcred.domain.model.Person;
import dev.matheuspereira.fluxcred.domain.ports.driven.IPersonRepository;
import dev.matheuspereira.fluxcred.domain.ports.driver.ILoanConfigService;
import dev.matheuspereira.fluxcred.domain.ports.driver.IPersonService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService implements IPersonService {
  private final IPersonRepository personRepository;
  private final ILoanConfigService loanConfigService;

  @Override
  public Person create(Person person) {
    personRepository.findByIdentifier(person.getIdentifier())
        .ifPresent(p -> {
          throw new BusinessException("The identifier already exists", 422);
        });

    person.setIdentifierType(IdentifierType.fromLength(person.getIdentifier().length()));

    var loanConfig = loanConfigService.getByIdentifierType(person.getIdentifierType().name());
    person.setMinMonthlyPayment(loanConfig.getMinMonthlyPayment());
    person.setMaxLoanAmount(loanConfig.getMaxLoanAmount());

    try {
      return personRepository.save(person);
    } catch (Exception e) {
      log.error("Person not saved", e);
      throw new BusinessException("Could not save the person, there was an internal error", 500);
    }
  }

  @Override
  public Person getById(Integer id) {
    return personRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public Person update(Integer id, Person personPatch) {
    var person = personRepository.findById(id)
        .orElseThrow(() -> new BusinessException("The person not exists", 404));

    personRepository.findByIdentifier(personPatch.getIdentifier())
        .ifPresent(p -> {
          if (!p.getIdentifier().equalsIgnoreCase(personPatch.getIdentifier())) {
            throw new BusinessException("The identifier already exists", 422);
          }
        });

    applyPatchToPerson(personPatch, person);
    try {
      return personRepository.save(person);
    } catch (Exception e) {
      log.error("Person not saved", e);
      throw new BusinessException("Could not save the person, there was an internal error", 500);
    }
  }

  @Override
  public void delete(Integer id) {
    personRepository.delete(id);
  }

  private void applyPatchToPerson(Person personPatch, Person person) {
    if (StringUtils.isNotEmpty(personPatch.getName())) {
      person.setName(personPatch.getName());
    }

    if (StringUtils.isNotEmpty(personPatch.getIdentifier())) {
      person.setIdentifier(personPatch.getIdentifier());
      person.setIdentifierType(IdentifierType.fromLength(person.getIdentifier().length()));
    }
  }
}
