package dev.matheuspereira.fluxcred.core.infrastructure.jpa;

import dev.matheuspereira.fluxcred.core.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends CrudRepository<UserEntity, Integer> {
  Optional<UserEntity> findByEmail(String email);
}