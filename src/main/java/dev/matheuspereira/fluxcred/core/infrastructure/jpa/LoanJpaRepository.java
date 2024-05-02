package dev.matheuspereira.fluxcred.core.infrastructure.jpa;

import dev.matheuspereira.fluxcred.core.infrastructure.entity.LoanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanJpaRepository extends CrudRepository<LoanEntity, Integer> {
}