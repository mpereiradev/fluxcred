package dev.matheuspereira.fluxcred.core.domain.ports.driver;

import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import dev.matheuspereira.fluxcred.core.domain.model.LoanConfig;

public interface ILoanConfigService extends IBaseService<LoanConfig> {
  LoanConfig getByIdentifierType(IdentifierType identifierType);
}
