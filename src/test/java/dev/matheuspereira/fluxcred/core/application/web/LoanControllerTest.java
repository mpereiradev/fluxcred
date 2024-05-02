package dev.matheuspereira.fluxcred.core.application.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.matheuspereira.fluxcred.core.application.data.request.LoanRequest;
import dev.matheuspereira.fluxcred.core.application.data.response.LoanResponse;
import dev.matheuspereira.fluxcred.core.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.core.domain.model.Loan;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.ILoanService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LoanControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ILoanService loanService;

  @MockBean
  private ModelMapper modelMapper;

  @Test
  @WithMockUser(roles = "USER")
  void createLoanShouldReturnCreatedLoan() throws Exception {
    LoanRequest request = new LoanRequest();
    Loan loan = new Loan();
    LoanResponse response = new LoanResponse();

    when(modelMapper.map(request, Loan.class)).thenReturn(loan);
    when(loanService.create(loan)).thenReturn(loan);
    when(modelMapper.map(loan, LoanResponse.class)).thenReturn(response);

    mockMvc.perform(post("/loans")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser(roles = "USER")
  void createLoanShouldReturnBadRequestWhenInputIsInvalid() throws Exception {
    when(modelMapper.map(any(LoanRequest.class), eq(Loan.class))).thenThrow(new BusinessException("Invalid input data", 400));

    mockMvc.perform(post("/loans")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"invalidField\":\"value\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "USER")
  void getLoanShouldReturnLoan() throws Exception {
    Loan loan = new Loan();
    LoanResponse response = new LoanResponse();

    when(loanService.getById(1)).thenReturn(loan);
    when(modelMapper.map(loan, LoanResponse.class)).thenReturn(response);

    mockMvc.perform(get("/loans/{id}", 1))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "USER")
  void getLoanShouldReturnNotFoundWhenLoanDoesNotExist() throws Exception {
    when(loanService.getById(anyInt())).thenThrow(new BusinessException("Loan not found", 404));

    mockMvc.perform(get("/loans/{id}", 1))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateLoanShouldReturnUpdatedLoan() throws Exception {
    LoanRequest request = new LoanRequest();
    Loan loanPatch = new Loan();
    Loan updatedLoan = new Loan();
    LoanResponse response = new LoanResponse();

    when(modelMapper.map(request, Loan.class)).thenReturn(loanPatch);
    when(loanService.update(1, loanPatch)).thenReturn(updatedLoan);
    when(modelMapper.map(updatedLoan, LoanResponse.class)).thenReturn(response);

    mockMvc.perform(patch("/loans/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateLoanShouldReturnNotFoundWhenLoanDoesNotExist() throws Exception {
    when(modelMapper.map(any(LoanRequest.class), eq(Loan.class))).thenReturn(new Loan());
    when(loanService.update(anyInt(), any(Loan.class))).thenThrow(new BusinessException("Loan not found", 404));

    mockMvc.perform(patch("/loans/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"amount\":1000}"))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void cancelLoanShouldReturnCancelledLoan() throws Exception {
    Loan cancelledLoan = new Loan();
    LoanResponse response = new LoanResponse();

    when(loanService.cancel(1)).thenReturn(cancelledLoan);
    when(modelMapper.map(cancelledLoan, LoanResponse.class)).thenReturn(response);

    mockMvc.perform(delete("/loans/{id}", 1))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void cancelLoanShouldReturnNotFoundWhenLoanDoesNotExist() throws Exception {
    when(loanService.cancel(anyInt())).thenThrow(new BusinessException("Loan not found", 404));

    mockMvc.perform(delete("/loans/{id}", 1))
        .andExpect(status().isNotFound());
  }
}
