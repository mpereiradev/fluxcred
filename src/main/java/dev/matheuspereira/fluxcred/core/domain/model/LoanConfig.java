package dev.matheuspereira.fluxcred.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanConfig {
  private Integer id;
  private IdentifierType identifierType;
  private BigDecimal minMonthlyPayment;
  private BigDecimal maxLoanAmount;
}
