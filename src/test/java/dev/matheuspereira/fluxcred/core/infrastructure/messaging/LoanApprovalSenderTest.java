package dev.matheuspereira.fluxcred.core.infrastructure.messaging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoanApprovalSenderTest {
  @Mock
  private RabbitTemplate rabbitTemplate;

  @InjectMocks
  private LoanApprovalSender loanApprovalSender;

  @Test
  void testSendLoanApprovedMessage() {
    String testMessage = "Test loan approval message";
    String testExchange = "test.exchange";
    String testRoutingKey = "test.routingKey";

    ReflectionTestUtils.setField(loanApprovalSender, "exchange", testExchange);
    ReflectionTestUtils.setField(loanApprovalSender, "routingKey", testRoutingKey);


    loanApprovalSender.sendLoanApprovedMessage(testMessage);
    verify(rabbitTemplate).convertAndSend(eq(testExchange), eq(testRoutingKey), eq(testMessage));
  }
}