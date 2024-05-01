package dev.matheuspereira.fluxcred.application.data.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthRequest {
  @NotNull
  @Schema(
      description = "The email to authentication",
      example = "matheus@smrasset.com",
      implementation = String.class,
      type = "String")
  private String email;

  @NotNull
  @Schema(
      description = "The password to authentication",
      example = "123456Aa",
      implementation = String.class,
      type = "String")
  private String password;
}
