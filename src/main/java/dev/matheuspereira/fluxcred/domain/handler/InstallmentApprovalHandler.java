package dev.matheuspereira.fluxcred.domain.handler;

import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.domain.model.Person;
import java.time.LocalDateTime;

public class InstallmentApprovalHandler implements LoanApprovalHandler {
  private LoanApprovalHandler next = new DirectApprovalHandler();

  @Override
  public void setNext(LoanApprovalHandler next) {
    this.next = next;
  }

  @Override
  public void handle(Loan loan, Person person) {
    double installmentValue = loan.getAmount() / loan.getNumberOfInstallments();

    if (installmentValue < person.getMinMonthlyPayment() || loan.getNumberOfInstallments() > 24) {
      loan.setStatus(LoanStatus.DISAPPROVED);
      loan.setApprovalDate(LocalDateTime.now());
      throw new BusinessException(
          "Loan fails. Installment value below the minimum or number of installments greater than 24",
          422);
    }

    next.handle(loan, person);
  }
}
