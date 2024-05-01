package dev.matheuspereira.fluxcred.domain.ports.driven;

public interface ILoanApprovalSender {
  void sendLoanApprovedMessage(String message);
}
