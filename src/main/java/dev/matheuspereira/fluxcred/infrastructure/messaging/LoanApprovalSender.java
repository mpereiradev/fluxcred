package dev.matheuspereira.fluxcred.infrastructure.messaging;

import dev.matheuspereira.fluxcred.domain.ports.driven.ILoanApprovalSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanApprovalSender implements ILoanApprovalSender {
  private final RabbitTemplate rabbitTemplate;

  @Value("${fluxcred.rabbitmq.exchange}")
  private String exchange;

  @Value("${fluxcred.rabbitmq.loan.approval.routing_key}")
  private String routingKey;

  @Override
  public void sendLoanApprovedMessage(String message) {
    rabbitTemplate.convertAndSend(exchange, routingKey, message);
    log.info("Sent Loan Approval Message: " + message);
  }

}
