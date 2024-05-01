package dev.matheuspereira.fluxcred.infrastructure.jpa;

import dev.matheuspereira.fluxcred.infrastructure.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonJpaRepository extends CrudRepository<PersonEntity, Integer> {
  Optional<PersonEntity> findByIdentifier(String identifier);
}