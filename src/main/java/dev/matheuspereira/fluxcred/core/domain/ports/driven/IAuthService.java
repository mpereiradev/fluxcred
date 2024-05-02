package dev.matheuspereira.fluxcred.core.domain.ports.driven;

import dev.matheuspereira.fluxcred.core.application.data.response.JwtResponse;
import dev.matheuspereira.fluxcred.core.domain.model.AuthCredentials;
import dev.matheuspereira.fluxcred.core.domain.model.User;

public interface IAuthService {
  User register(User user);
  JwtResponse auth(AuthCredentials credentials);
}
