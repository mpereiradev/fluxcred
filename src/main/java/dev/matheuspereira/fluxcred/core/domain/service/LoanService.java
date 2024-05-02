package dev.matheuspereira.fluxcred.core.domain.service;

import dev.matheuspereira.fluxcred.core.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.core.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.core.domain.handler.LoanApprovalHandler;
import dev.matheuspereira.fluxcred.core.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.core.domain.model.Person;
import dev.matheuspereira.fluxcred.core.domain.ports.driven.ILoanRepository;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.IPersonService;
import dev.matheuspereira.fluxcred.core.domain.handler.InstallmentApprovalHandler;
import dev.matheuspereira.fluxcred.core.domain.model.Loan;
import dev.matheuspereira.fluxcred.core.domain.ports.driven.ILoanApprovalSender;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.ILoanService;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService implements ILoanService {
  private final ILoanRepository loanRepository;
  private final IPersonService personService;
  private final ILoanApprovalSender loanApprovalSender;

  @Override
  public Loan create(Loan loan) {
    Person person = personService.getByIdentifier(loan.getPersonIdentifier());

    LoanApprovalHandler approvalHandler = new InstallmentApprovalHandler();
    approvalHandler.handle(loan, person);
    loanApprovalSender.sendLoanApprovedMessage(loan.toString());

    try {
      personService.save(person);
      return loanRepository.save(loan);
    } catch (Exception e) {
      log.error("Loan not saved", e);
      throw new BusinessException("Could not save the loan, there was an internal error", 500);
    }
  }

  @Override
  public Loan getById(Integer id) {
    return loanRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public Loan update(Integer id, Loan loanPatch) {
    var loan =
        loanRepository
            .findById(id)
            .orElseThrow(() -> new BusinessException("The loan not exists", 404));

    if (loan.getStatus() != LoanStatus.STARTED
        && loan.getStatus() != LoanStatus.AWAITING_APPROVAL) {
      throw new BusinessException(
          "The loan cannot be edited in status: " + loan.getStatus().name(), 422);
    }

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
    throw new BusinessException("Deleting a loan is not allowed, use the cancel feature", 422);
  }

  @Override
  public Loan cancel(Integer id) {
    var loan =
        loanRepository
            .findById(id)
            .orElseThrow(() -> new BusinessException("The loan not exists", 404));

    if (loan.getStatus() == LoanStatus.IN_PROGRESS
        || loan.getStatus() == LoanStatus.FINISHED
        || loan.getStatus() == LoanStatus.DELINQUENCY) {
      throw new BusinessException(
          "The loan cannot be deleted in status: " + loan.getStatus().name(), 422);
    }

    loan.setStatus(LoanStatus.CANCELED);
    loan.setCancellationDate(LocalDateTime.now());
    return loanRepository.save(loan);
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
