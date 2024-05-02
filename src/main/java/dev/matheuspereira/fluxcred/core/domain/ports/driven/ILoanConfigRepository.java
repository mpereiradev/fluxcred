package dev.matheuspereira.fluxcred.core.domain.ports.driven;

import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import dev.matheuspereira.fluxcred.core.domain.model.LoanConfig;

import java.util.Optional;

public interface ILoanConfigRepository extends IBaseRepository<LoanConfig> {
  Optional<LoanConfig> findByIdentifierType(IdentifierType identifierType);
}
