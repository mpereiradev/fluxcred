package dev.matheuspereira.fluxcred.domain.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InstallmentApprovalHandlerTest {

  @Mock private LoanApprovalHandler nextHandler;

  @InjectMocks private InstallmentApprovalHandler handler;

  @Test
  void shouldSetNextHandlerCorrectly() {
    LoanApprovalHandler newNextHandler = mock(LoanApprovalHandler.class);
    handler.setNext(newNextHandler);
    handler.handle(new Loan(), new Person()); // Call handle to trigger next handler
    verify(newNextHandler).handle(any(), any());
  }

  @Test
  void shouldApproveLoanWhenConditionsAreMet() {
    Loan loan = new Loan();
    loan.setAmount(5000);
    loan.setNumberOfInstallments(20);
    Person person = new Person();
    person.setMinMonthlyPayment(200);

    handler.handle(loan, person);

    verify(nextHandler).handle(loan, person);
    assertNotEquals(LoanStatus.DISAPPROVED, loan.getStatus());
  }

  @Test
  void shouldDisapproveLoanWhenInstallmentValueTooLow() {
    Loan loan = new Loan();
    loan.setAmount(1000);
    loan.setNumberOfInstallments(12); // Less than 24 but installment value will be too low
    Person person = new Person();
    person.setMinMonthlyPayment(100);

    BusinessException exception =
        assertThrows(BusinessException.class, () -> handler.handle(loan, person));

    assertEquals(LoanStatus.DISAPPROVED, loan.getStatus());
    assertNotNull(loan.getApprovalDate());
    assertEquals(
        "Loan fails. Installment value below the minimum or number of installments greater than 24",
        exception.getMessage());
  }

  @Test
  void shouldDisapproveLoanWhenNumberOfInstallmentsTooHigh() {
    Loan loan = new Loan();
    loan.setAmount(5000);
    loan.setNumberOfInstallments(30); // Greater than 24
    Person person = new Person();
    person.setMinMonthlyPayment(200);

    BusinessException exception =
        assertThrows(BusinessException.class, () -> handler.handle(loan, person));

    assertEquals(LoanStatus.DISAPPROVED, loan.getStatus());
    assertNotNull(loan.getApprovalDate());
    assertEquals(
        "Loan fails. Installment value below the minimum or number of installments greater than 24",
        exception.getMessage());
  }
}
