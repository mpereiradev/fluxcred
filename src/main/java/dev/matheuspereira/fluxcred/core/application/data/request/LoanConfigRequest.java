package dev.matheuspereira.fluxcred.core.application.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanConfigRequest {
  @Schema(description = "Identifier type for the loan configuration (PF, PJ, EU, AP)", example = "PF")
  private String identifierType;

  @Schema(description = "Minimum monthly payment for the loan", example = "300.00")
  private double minMonthlyPayment;

  @Schema(description = "Maximum amount of the loan", example = "10000.00")
  private double maxLoanAmount;
}
