package dev.matheuspereira.fluxcred.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  private Integer id;
  private String name;
  private LocalDate birthDate;
  private String identifier;
  private IdentifierType identifierType;
  private BigDecimal minMonthlyPayment;
  private BigDecimal maxLoanAmount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
