package dev.matheuspereira.fluxcred.core.infrastructure.jpa;

import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import dev.matheuspereira.fluxcred.core.infrastructure.entity.LoanConfigEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanConfigJpaRepository extends CrudRepository<LoanConfigEntity, Integer> {
  Optional<LoanConfigEntity> findByIdentifierType(IdentifierType identifierType);
}