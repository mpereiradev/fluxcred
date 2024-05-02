package dev.matheuspereira.fluxcred.core.domain.handler;

import dev.matheuspereira.fluxcred.core.domain.model.Loan;
import dev.matheuspereira.fluxcred.core.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.core.domain.model.Person;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ManualApprovalHandler implements LoanApprovalHandler {
  private LoanApprovalHandler next;

  @Override
  public void setNext(LoanApprovalHandler next) {
    // No next handler, this is the end of the chain
  }

  @Override
  public void handle(Loan loan, Person person) {
    BigDecimal tenPercentAbove = person.getMaxLoanAmount().multiply(new BigDecimal("1.1"));
    if (loan.getAmount().compareTo(tenPercentAbove) <= 0) {
      loan.setStatus(LoanStatus.AWAITING_APPROVAL);
      person.setMaxLoanAmount(person.getMaxLoanAmount().subtract(loan.getAmount()));
    } else {
      loan.setStatus(LoanStatus.DISAPPROVED);
      loan.setApprovalDate(LocalDateTime.now());
    }
  }
}
