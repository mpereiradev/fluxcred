package dev.matheuspereira.fluxcred.domain.ports.driven;

import dev.matheuspereira.fluxcred.domain.model.User;

public interface IUserRepository {
  User save(User user);
}
