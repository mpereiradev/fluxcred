package dev.matheuspereira.fluxcred.core.application.data.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
  @Schema(description = "The ID of the person", example = "1")
  private Integer id;

  @Schema(description = "The full name of the person", example = "Matheus C Pereira")
  private String name;

  @Schema(description = "The unique identifier for the person (e.g., CPF, CNPJ)", example = "123456789012")
  private String identifier;

  @Schema(description = "The type of identifier", example = "PF")
  private String identifierType;

  @Schema(description = "The birth date of the person", example = "1985-04-12", type = "string", format = "date")
  private LocalDate birthDate;

  @Schema(description = "The minimum monthly payment value", example = "300.00")
  private double minMonthlyPayment;

  @Schema(description = "The maximum loan amount", example = "10000.00")
  private double maxLoanAmount;

  @Schema(description = "The timestamp when the person was created", example = "2024-01-01T12:00:00", type = "string", format = "date-time")
  private LocalDateTime createdAt;

  @Schema(description = "The timestamp when the person's information was last updated", example = "2024-01-02T15:00:00", type = "string", format = "date-time")
  private LocalDateTime updatedAt;
}
