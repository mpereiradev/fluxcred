package dev.matheuspereira.fluxcred.domain.service;

import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.domain.ports.driven.ILoanRepository;
import dev.matheuspereira.fluxcred.domain.ports.driver.ILoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService implements ILoanService {
  private final ILoanRepository loanRepository;

  @Override
  public Loan create(Loan model) {
    return null;
  }

  @Override
  public Loan getById(Integer id) {
    return loanRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public Loan update(Integer id, Loan loanPatch) {
    var loan = loanRepository.findById(id)
        .orElseThrow(() -> new BusinessException("The loan not exists", 404));

    applyPatchToModel(loanPatch, loan);
    try {
      return loanRepository.save(loan);
    } catch (Exception e) {
      log.error("Loan not saved", e);
      throw new BusinessException("Could not save the loan, there was an internal error", 500);
    }
  }

  @Override
  public void delete(Integer id) {
    loanRepository.delete(id);
  }

  private void applyPatchToModel(Loan loanPatch, Loan loan) {
    if (loanPatch.getAmount() > 0) {
      loan.setAmount(loanPatch.getAmount());
    }

    if (loanPatch.getNumberOfInstallments() > 0) {
      loan.setNumberOfInstallments(loanPatch.getNumberOfInstallments());
    }
  }
}
