package dev.matheuspereira.fluxcred.core.domain.handler;

import static org.junit.jupiter.api.Assertions.*;

import dev.matheuspereira.fluxcred.core.domain.model.Loan;
import dev.matheuspereira.fluxcred.core.domain.model.LoanStatus;
import dev.matheuspereira.fluxcred.core.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ManualApprovalHandlerTest {

  @InjectMocks private ManualApprovalHandler handler;

  @Test
  void shouldAwaitApprovalWhenAmountIsWithinTenPercentAboveLimit() {
    Loan loan = new Loan();
    loan.setAmount(BigDecimal.valueOf(11000));
    Person person = new Person();
    person.setMaxLoanAmount(BigDecimal.valueOf(10000));

    handler.handle(loan, person);

    assertEquals(LoanStatus.AWAITING_APPROVAL, loan.getStatus());
    assertEquals(0, BigDecimal.valueOf(-1000).compareTo(person.getMaxLoanAmount()));
  }

  @Test
  void shouldDisapproveWhenAmountExceedsTenPercentAboveLimit() {
    Loan loan = new Loan();
    loan.setAmount(BigDecimal.valueOf(15000));
    Person person = new Person();
    person.setMaxLoanAmount(BigDecimal.valueOf(10000));

    handler.handle(loan, person);

    assertEquals(LoanStatus.DISAPPROVED, loan.getStatus());
    assertNotNull(loan.getApprovalDate());
    assertEquals(0, BigDecimal.valueOf(10000).compareTo(person.getMaxLoanAmount()));
  }
}
