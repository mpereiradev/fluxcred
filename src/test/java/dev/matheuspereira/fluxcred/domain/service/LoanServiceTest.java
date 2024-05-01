package dev.matheuspereira.fluxcred.domain.service;

import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.domain.model.Person;
import dev.matheuspereira.fluxcred.domain.ports.driven.ILoanApprovalSender;
import dev.matheuspereira.fluxcred.domain.ports.driven.ILoanRepository;
import dev.matheuspereira.fluxcred.domain.ports.driver.IPersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

  @Mock
  private ILoanRepository loanRepository;

  @Mock
  private IPersonService personService;

  @Mock
  private ILoanApprovalSender loanApprovalSender;

  @InjectMocks
  private LoanService loanService;

  @Test
  void createLoanSuccessful() {
    Loan loan = new Loan();
    loan.setPersonIdentifier("12345678");
    Person person = new Person();
    when(personService.getByIdentifier(anyString())).thenReturn(person);
    when(loanRepository.save(any(Loan.class))).thenReturn(loan);

    loanService.create(loan);

    verify(personService).save(person);
    verify(loanRepository).save(loan);
    verify(loanApprovalSender).sendLoanApprovedMessage(loan.toString());
  }

  @Test
  void createLoanThrowsBusinessException() {
    Loan loan = new Loan();
    loan.setPersonIdentifier("12345678");
    when(personService.getByIdentifier(anyString())).thenReturn(new Person());
    when(loanRepository.save(any(Loan.class))).thenThrow(new RuntimeException());

    assertThrows(BusinessException.class, () -> loanService.create(loan));
  }

  @Test
  void getLoanByIdSuccessful() {
    Loan loan = new Loan();
    when(loanRepository.findById(anyInt())).thenReturn(Optional.of(loan));

    Loan foundLoan = loanService.getById(1);

    assertEquals(loan, foundLoan);
  }

  @Test
  void getLoanByIdThrowsNotFoundException() {
    when(loanRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> loanService.getById(1));
  }

  @Test
  void updateLoanSuccessful() {
    Loan existingLoan = new Loan();
    existingLoan.setStatus(LoanStatus.STARTED);
    Loan loanPatch = new Loan();
    loanPatch.setAmount(1000);
    when(loanRepository.findById(anyInt())).thenReturn(Optional.of(existingLoan));
    when(loanRepository.save(any(Loan.class))).thenReturn(existingLoan);

    Loan updatedLoan = loanService.update(1, loanPatch);

    assertEquals(1000, updatedLoan.getAmount());
  }

  @Test
  void updateLoanThrowsBusinessExceptionForStatus() {
    Loan existingLoan = new Loan();
    existingLoan.setStatus(LoanStatus.FINISHED);
    when(loanRepository.findById(anyInt())).thenReturn(Optional.of(existingLoan));

    assertThrows(BusinessException.class, () -> loanService.update(1, new Loan()));
  }

  @Test
  void cancelLoanSuccessful() {
    Loan existingLoan = new Loan();
    existingLoan.setStatus(LoanStatus.AWAITING_APPROVAL);
    when(loanRepository.findById(anyInt())).thenReturn(Optional.of(existingLoan));
    when(loanRepository.save(any(Loan.class))).thenReturn(existingLoan);

    Loan cancelledLoan = loanService.cancel(1);

    assertEquals(LoanStatus.CANCELED, cancelledLoan.getStatus());
    assertNotNull(cancelledLoan.getCancellationDate());
  }

  @Test
  void cancelLoanThrowsBusinessExceptionForStatus() {
    Loan existingLoan = new Loan();
    existingLoan.setStatus(LoanStatus.IN_PROGRESS);
    when(loanRepository.findById(anyInt())).thenReturn(Optional.of(existingLoan));

    assertThrows(BusinessException.class, () -> loanService.cancel(1));
  }

  @Test
  void deleteLoanThrowsBusinessException() {
    assertThrows(BusinessException.class, () -> loanService.delete(1));
  }
}
