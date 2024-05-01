package dev.matheuspereira.fluxcred.application.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {
  @NotBlank(message = "Name cannot be empty")
  @Schema(description = "The full name of the person", example = "Matheus C Pereira")
  private String name;

  @NotBlank(message = "Identifier cannot be empty")
  @Size(min = 8, max = 14, message = "Identifier must be between 8 and 14 characters")
  @Schema(description = "The unique identifier for the person (e.g., CPF, CNPJ)", example = "123456789012")
  private String identifier;

  @NotNull(message = "Birth date cannot be null")
  @Schema(description = "The birth date of the person", example = "1985-04-12", type = "string", format = "date")
  private LocalDate birthDate;
}
