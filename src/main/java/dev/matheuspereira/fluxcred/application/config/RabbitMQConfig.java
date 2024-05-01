package dev.matheuspereira.fluxcred.application.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${fluxcred.rabbitmq.loan.approval.queue}")
  private String loanApprovalQueue;

  @Value("${fluxcred.rabbitmq.loan.approval.routing_key}")
  private String loanApprovalRoutingKey;

  @Value("${fluxcred.rabbitmq.exchange}")
  private String exchange;

  @Bean
  Queue loanApprovalQueue() {
    return new Queue(loanApprovalQueue, true);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  Binding binding(@Qualifier("loanApprovalQueue") Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(loanApprovalRoutingKey + ".#");
  }
}
