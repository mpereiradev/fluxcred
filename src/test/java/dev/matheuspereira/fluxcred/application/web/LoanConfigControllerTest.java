package dev.matheuspereira.fluxcred.application.web;

import dev.matheuspereira.fluxcred.application.config.RabbitMQConfig;
import dev.matheuspereira.fluxcred.application.data.request.LoanConfigRequest;
import dev.matheuspereira.fluxcred.application.data.response.LoanConfigResponse;
import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.model.LoanConfig;
import dev.matheuspereira.fluxcred.domain.ports.driver.ILoanConfigService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoanConfigControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ILoanConfigService loanConfigService;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean
  private RabbitMQConfig rabbitMQConfig;

  @Test
  void createLoanConfigShouldReturnCreated() throws Exception {
    LoanConfigRequest request = new LoanConfigRequest(); // Setup request
    LoanConfig loanConfig = new LoanConfig();
    LoanConfigResponse response = new LoanConfigResponse();

    when(modelMapper.map(request, LoanConfig.class)).thenReturn(loanConfig);
    when(loanConfigService.create(loanConfig)).thenReturn(loanConfig);
    when(modelMapper.map(loanConfig, LoanConfigResponse.class)).thenReturn(response);

    mockMvc.perform(post("/loan-configs/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isCreated());
  }

  @Test
  void createLoanConfigShouldReturnBadRequestOnBusinessException() throws Exception {
    when(modelMapper.map(any(LoanConfigRequest.class), any())).thenThrow(new BusinessException("Invalid data", 400));

    mockMvc.perform(post("/loan-configs/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getLoanConfigShouldReturnLoanConfig() throws Exception {
    LoanConfig loanConfig = new LoanConfig();
    LoanConfigResponse response = new LoanConfigResponse();

    when(loanConfigService.getByIdentifierType(anyString())).thenReturn(loanConfig);
    when(modelMapper.map(loanConfig, LoanConfigResponse.class)).thenReturn(response);

    mockMvc.perform(get("/loan-configs/{identifierType}", "PERSONAL"))
        .andExpect(status().isOk());
  }

  @Test
  void updateLoanConfigShouldReturnUpdatedLoanConfig() throws Exception {
    LoanConfigRequest request = new LoanConfigRequest();
    LoanConfig loanConfig = new LoanConfig();
    LoanConfigResponse response = new LoanConfigResponse();

    when(modelMapper.map(request, LoanConfig.class)).thenReturn(loanConfig);
    when(loanConfigService.update(anyInt(), any(LoanConfig.class))).thenReturn(loanConfig);
    when(modelMapper.map(loanConfig, LoanConfigResponse.class)).thenReturn(response);

    mockMvc.perform(patch("/loan-configs/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isOk());
  }

  @Test
  void deleteLoanConfigShouldReturnNoContent() throws Exception {
    doNothing().when(loanConfigService).delete(anyInt());

    mockMvc.perform(delete("/loan-configs/{id}", 1))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteLoanConfigShouldReturnBadRequestOnBusinessException() throws Exception {
    doThrow(new BusinessException("Cannot delete", 400)).when(loanConfigService).delete(anyInt());

    mockMvc.perform(delete("/loan-configs/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
