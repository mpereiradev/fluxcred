package dev.matheuspereira.fluxcred.domain.handler;

import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.domain.model.Person;

import java.time.LocalDateTime;

public class DirectApprovalHandler implements LoanApprovalHandler {
  private LoanApprovalHandler next = new ManualApprovalHandler();

  @Override
  public void setNext(LoanApprovalHandler next) {
    this.next = next;
  }

  @Override
  public void handle(Loan loan, Person person) {
    if (loan.getAmount() <= person.getMaxLoanAmount()) {
      loan.setStatus(LoanStatus.APPROVED);
      loan.setApprovalDate(LocalDateTime.now());
      person.setMaxLoanAmount(person.getMaxLoanAmount() - loan.getAmount());
    } else {
      next.handle(loan, person);
    }
  }
}
