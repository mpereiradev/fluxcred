package dev.matheuspereira.fluxcred.core.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuspereira.fluxcred.core.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.ILoanInstallmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanInstallmentPaymentConsumer {
  private final ObjectMapper objectMapper;
  private final ILoanInstallmentService loanInstallmentService;

  @RabbitListener(queues = {"${fluxcred.rabbitmq.loan.approval.queue}"})
  public void receiveMessage(@Payload String message) {
    try {
      LoanInstallment installment = objectMapper.readValue(message, LoanInstallment.class);
      loanInstallmentService.payment(installment);
    } catch (Exception e) {
      log.error("Error processing loanApprovalMessage", e);
    }
  }
}
