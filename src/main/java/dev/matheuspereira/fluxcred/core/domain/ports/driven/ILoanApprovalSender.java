package dev.matheuspereira.fluxcred.core.domain.ports.driven;

public interface ILoanApprovalSender {
  void sendLoanApprovedMessage(String message);
}
