package dev.matheuspereira.fluxcred.infrastructure.jpa;

import dev.matheuspereira.fluxcred.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends CrudRepository<UserEntity, Integer> {
  Optional<UserEntity> findByEmail(String email);
}