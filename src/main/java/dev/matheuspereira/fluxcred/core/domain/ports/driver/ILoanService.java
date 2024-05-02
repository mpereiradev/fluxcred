package dev.matheuspereira.fluxcred.core.domain.ports.driver;

import dev.matheuspereira.fluxcred.core.domain.model.Loan;

public interface ILoanService extends IBaseService<Loan> {
  Loan cancel(Integer id);
}
