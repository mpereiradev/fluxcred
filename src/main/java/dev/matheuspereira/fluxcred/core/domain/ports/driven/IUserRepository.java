package dev.matheuspereira.fluxcred.core.domain.ports.driven;

import dev.matheuspereira.fluxcred.core.domain.model.User;

public interface IUserRepository {
  User save(User user);
}
