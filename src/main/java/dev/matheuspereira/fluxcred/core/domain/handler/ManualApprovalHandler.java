package dev.matheuspereira.fluxcred.core.domain.handler;

import dev.matheuspereira.fluxcred.core.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.core.domain.model.Person;
import dev.matheuspereira.fluxcred.core.domain.model.Loan;

import java.time.LocalDateTime;

public class ManualApprovalHandler implements LoanApprovalHandler {
  private LoanApprovalHandler next;
  @Override
  public void setNext(LoanApprovalHandler next) {
    // No next handler, this is the end of the chain
  }

  @Override
  public void handle(Loan loan, Person person) {
    double tenPercentAbove = person.getMaxLoanAmount() * 1.1;
    if (loan.getAmount() <= tenPercentAbove) {
      loan.setStatus(LoanStatus.AWAITING_APPROVAL);
      person.setMaxLoanAmount(person.getMaxLoanAmount() - loan.getAmount());
    } else {
      loan.setStatus(LoanStatus.DISAPPROVED);
      loan.setApprovalDate(LocalDateTime.now());
    }
  }
}
