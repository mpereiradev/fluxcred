package dev.matheuspereira.fluxcred.domain.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DirectApprovalHandlerTest {

  @Mock private LoanApprovalHandler nextHandler;

  @InjectMocks private DirectApprovalHandler handler;

  @Test
  void shouldApproveLoanWhenAmountIsWithinLimit() {
    Loan loan = new Loan();
    loan.setAmount(5000);
    Person person = new Person();
    person.setMaxLoanAmount(10000);

    handler.handle(loan, person);

    assertEquals(LoanStatus.APPROVED, loan.getStatus());
    assertNotNull(loan.getApprovalDate());
    assertEquals(5000, person.getMaxLoanAmount());
    verifyNoInteractions(nextHandler);
  }

  @Test
  void shouldPassToNextHandlerWhenAmountExceedsLimit() {
    Loan loan = new Loan();
    loan.setAmount(11000);
    Person person = new Person();
    person.setMaxLoanAmount(10000);

    handler.handle(loan, person);

    verify(nextHandler).handle(loan, person);
    assertNotEquals(LoanStatus.APPROVED, loan.getStatus());
  }

  @Test
  void shouldSetNextHandlerCorrectly() {
    LoanApprovalHandler newNextHandler = mock(LoanApprovalHandler.class);
    handler.setNext(newNextHandler);
    Loan loan = new Loan();
    loan.setAmount(12000);
    Person person = new Person();
    person.setMaxLoanAmount(10000);

    handler.handle(loan, person);

    verify(newNextHandler).handle(loan, person);
    assertNotEquals(LoanStatus.APPROVED, loan.getStatus());
  }
}
