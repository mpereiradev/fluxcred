package dev.matheuspereira.fluxcred.core.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuspereira.fluxcred.core.domain.model.Loan;
import dev.matheuspereira.fluxcred.core.domain.ports.driven.ILoanApprovalSender;
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
  private final ObjectMapper objectMapper;

  @Value("${fluxcred.rabbitmq.exchange}")
  private String exchange;

  @Value("${fluxcred.rabbitmq.loan.approval.routing_key}")
  private String routingKey;

  @Override
  public void sendLoanApprovedMessage(Loan loan) {
    try {
      String message = objectMapper.writeValueAsString(loan);
      rabbitTemplate.convertAndSend(exchange, routingKey, message);
      log.info("Sent Loan Approval Message: " + message);
    } catch (JsonProcessingException e) {
      log.error("Unable to convert loan to json", e);
    } catch (Exception e) {
      log.error("Error sending loanApprovalMessage", e);
    }
  }

}
