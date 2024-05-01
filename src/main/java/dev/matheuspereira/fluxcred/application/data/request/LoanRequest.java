package dev.matheuspereira.fluxcred.application.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {

  @Schema(
      description = "The identifier of the person applying for the loan",
      example = "12345678901")
  private String personIdentifier;

  @Schema(description = "The amount of money requested for the loan", example = "5000")
  private BigDecimal amount;

  @Schema(
      description = "The number of installments in which the loan will be repaid",
      example = "12")
  private int numberOfInstallments;
}
