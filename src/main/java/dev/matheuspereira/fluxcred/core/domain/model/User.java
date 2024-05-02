package dev.matheuspereira.fluxcred.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String fullName;
  private String email;
  private String password;
  private UserRole role;
}
