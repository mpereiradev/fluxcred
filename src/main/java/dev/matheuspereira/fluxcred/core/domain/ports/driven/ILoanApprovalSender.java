package dev.matheuspereira.fluxcred.core.domain.ports.driven;

import dev.matheuspereira.fluxcred.core.domain.model.Loan;

public interface ILoanApprovalSender {
  void sendLoanApprovedMessage(Loan loan);
}
