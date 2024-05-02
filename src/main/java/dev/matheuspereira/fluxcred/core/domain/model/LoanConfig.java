package dev.matheuspereira.fluxcred.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanConfig {
  private Integer id;
  private IdentifierType identifierType;
  private double minMonthlyPayment;
  private double maxLoanAmount;
}
