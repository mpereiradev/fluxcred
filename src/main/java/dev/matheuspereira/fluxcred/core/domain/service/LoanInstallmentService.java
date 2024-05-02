package dev.matheuspereira.fluxcred.core.domain.service;

import dev.matheuspereira.fluxcred.core.domain.model.Loan;
import dev.matheuspereira.fluxcred.core.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.core.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.core.domain.model.Person;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.ILoanInstallmentService;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.ILoanService;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.IPersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanInstallmentService implements ILoanInstallmentService {
  private final ILoanService loanService;
  private final IPersonService personService;

  @Override
  public void payment(LoanInstallment installment) {
    Loan loan = loanService.getById(installment.getLoan().getId());
    Person person = personService.getByIdentifier(loan.getPersonIdentifier());

    person.setMaxLoanAmount(person.getMaxLoanAmount().add(installment.getAmount()));
    personService.save(person);

    if (installment.getInstallmentNumber() == loan.getNumberOfInstallments()) {
      loan.setStatus(LoanStatus.FINISHED);
      loanService.save(loan);
    }
  }

}
