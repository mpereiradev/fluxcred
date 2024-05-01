package dev.matheuspereira.fluxcred.domain.ports.driver;

import dev.matheuspereira.fluxcred.domain.model.LoanConfig;

public interface ILoanConfigService extends IBaseService<LoanConfig> {
  LoanConfig getByIdentifierType(String identifierType);
}
