package dev.matheuspereira.fluxcred.core.domain.handler;

import static org.junit.jupiter.api.Assertions.*;

import dev.matheuspereira.fluxcred.core.domain.model.Loan;
import dev.matheuspereira.fluxcred.core.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.core.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManualApprovalHandlerTest {

  @InjectMocks private ManualApprovalHandler handler;

  @Test
  void shouldAwaitApprovalWhenAmountIsWithinTenPercentAboveLimit() {
    Loan loan = new Loan();
    loan.setAmount(11000);
    Person person = new Person();
    person.setMaxLoanAmount(10000);

    handler.handle(loan, person);

    assertEquals(LoanStatus.AWAITING_APPROVAL, loan.getStatus());
    assertEquals(-1000, person.getMaxLoanAmount());
  }

  @Test
  void shouldDisapproveWhenAmountExceedsTenPercentAboveLimit() {
    Loan loan = new Loan();
    loan.setAmount(15000);
    Person person = new Person();
    person.setMaxLoanAmount(10000);

    handler.handle(loan, person);

    assertEquals(LoanStatus.DISAPPROVED, loan.getStatus());
    assertNotNull(loan.getApprovalDate());
    assertEquals(10000, person.getMaxLoanAmount());
  }
}
