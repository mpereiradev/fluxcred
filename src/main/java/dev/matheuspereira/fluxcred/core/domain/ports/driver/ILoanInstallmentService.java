package dev.matheuspereira.fluxcred.core.domain.ports.driver;

import dev.matheuspereira.fluxcred.core.domain.model.LoanInstallment;

public interface ILoanInstallmentService {

  void payment(LoanInstallment installment);
}
