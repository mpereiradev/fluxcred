package dev.matheuspereira.fluxcred.domain.ports.driven;

import dev.matheuspereira.fluxcred.application.data.response.JwtResponse;
import dev.matheuspereira.fluxcred.domain.model.AuthCredentials;
import dev.matheuspereira.fluxcred.domain.model.User;

public interface IAuthService {
  User register(User user);
  JwtResponse auth(AuthCredentials credentials);
}
