package dev.matheuspereira.fluxcred.core.application.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRegisterRequest {
  @NotNull
  @Schema(
      description = "The full name to register",
      example = "Matheus Costa Pereira",
      implementation = String.class,
      type = "String")
  private String fullName;

  @NotNull
  @Schema(
      description = "The email to register",
      example = "matheus@smrasset.com",
      implementation = String.class,
      type = "String")
  private String email;

  @NotNull
  @Schema(
      description = "The password to register",
      example = "123456Aa",
      implementation = String.class,
      type = "String")
  private String password;
}
