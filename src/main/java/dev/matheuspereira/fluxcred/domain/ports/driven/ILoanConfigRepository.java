package dev.matheuspereira.fluxcred.domain.ports.driven;

import dev.matheuspereira.fluxcred.domain.model.LoanConfig;

import java.util.Optional;

public interface ILoanConfigRepository extends IBaseRepository<LoanConfig> {
  Optional<LoanConfig> findByIdentifierType(String identifierType);
}
