package dev.matheuspereira.fluxcred.core.domain.handler;

import dev.matheuspereira.fluxcred.core.domain.model.Loan;
import dev.matheuspereira.fluxcred.core.domain.model.Person;

public interface LoanApprovalHandler {
  void setNext(LoanApprovalHandler next);
  void handle(Loan loan, Person person);
}
