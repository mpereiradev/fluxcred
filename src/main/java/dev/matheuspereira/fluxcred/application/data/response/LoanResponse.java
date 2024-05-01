package dev.matheuspereira.fluxcred.application.data.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {

  @Schema(description = "Unique identifier of the loan", example = "1")
  private Integer id;

  @Schema(description = "Identifier of the person who has taken the loan", example = "12345678901")
  private String personIdentifier;

  @Schema(description = "The total amount of the loan", example = "5000")
  private BigDecimal amount;

  @Schema(
      description = "The number of installments in which the loan will be repaid",
      example = "12")
  private int numberOfInstallments;

  @Schema(description = "The current status of the loan", example = "APPROVED")
  private String status;

  @Schema(description = "Date the first payment is due", example = "2024-01-01T12:00:00")
  private LocalDateTime firstPaymentDate;

  @Schema(description = "Date the loan was approved", example = "2023-12-25T15:00:00")
  private LocalDateTime approvalDate;

  @Schema(description = "Date the loan agreement was signed", example = "2023-12-26T10:00:00")
  private LocalDateTime signingDate;

  @Schema(description = "Date the loan became delinquent", example = "2024-02-01T12:00:00")
  private LocalDateTime delinquencyDate;

  @Schema(description = "Date the next payment is due", example = "2024-02-01T12:00:00")
  private LocalDateTime nextPaymentDate;

  @Schema(description = "Date the loan was fully paid", example = "2024-12-01T12:00:00")
  private LocalDateTime completionDate;

  @Schema(description = "Date the loan was cancelled", example = "2024-01-15T12:00:00")
  private LocalDateTime cancellationDate;

  @Schema(description = "Date the loan record was created", example = "2023-12-24T12:00:00")
  private LocalDateTime createdAt;

  @Schema(description = "Date the loan record was last updated", example = "2023-12-27T12:00:00")
  private LocalDateTime updatedAt;
}
