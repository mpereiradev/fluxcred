package dev.matheuspereira.fluxcred.infrastructure.jpa;

import dev.matheuspereira.fluxcred.infrastructure.entity.LoanConfigEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanConfigJpaRepository extends CrudRepository<LoanConfigEntity, Integer> {
  Optional<LoanConfigEntity> findByIdentifierType(String identifierType);
}