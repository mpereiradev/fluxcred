package dev.matheuspereira.fluxcred.application.web;

import dev.matheuspereira.fluxcred.application.config.RabbitMQConfig;
import dev.matheuspereira.fluxcred.application.data.response.JwtResponse;
import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.model.AuthCredentials;
import dev.matheuspereira.fluxcred.domain.model.User;
import dev.matheuspereira.fluxcred.domain.ports.driven.IAuthService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IAuthService authService;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean
  private RabbitMQConfig rabbitMQConfig;

  @Test
  void signInShouldReturnJwtWhenCredentialsAreValid() throws Exception {
    AuthCredentials credentials = new AuthCredentials("user", "password");
    JwtResponse jwtResponse = new JwtResponse("token123", new Date());
    when(modelMapper.map(any(), eq(AuthCredentials.class))).thenReturn(credentials);
    when(authService.auth(credentials)).thenReturn(jwtResponse);

    mockMvc.perform(post("/auth/singIn")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"user\",\"password\":\"password\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").value("token123"));
  }

  @Test
  void signInShouldReturnUnauthorizedWhenCredentialsAreInvalid() throws Exception {
    when(modelMapper.map(any(), eq(AuthCredentials.class))).thenReturn(new AuthCredentials("user", "password"));
    when(authService.auth(any(AuthCredentials.class))).thenThrow(new BusinessException("Invalid credentials", 401));

    mockMvc.perform(post("/auth/singIn")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"user\",\"password\":\"wrongpassword\"}"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void signUpShouldReturnSuccessMessageWhenUserIsRegistered() throws Exception {
    User user = new User();
    user.setFullName("Matheus C Pereira");
    when(modelMapper.map(any(), eq(User.class))).thenReturn(user);
    when(authService.register(user)).thenReturn(user);

    mockMvc.perform(post("/auth/singUp")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"johndoe\",\"password\":\"password\",\"fullName\":\"John Doe\"}"))
        .andExpect(status().isOk())
        .andExpect(content().string("Matheus C Pereira registered with successful"));
  }

  @Test
  void signUpShouldReturnInternalServerErrorWhenRegistrationFails() throws Exception {
    when(modelMapper.map(any(), eq(User.class))).thenReturn(new User());
    when(authService.register(any(User.class))).thenThrow(new BusinessException("Internal error", 500));

    mockMvc.perform(post("/auth/singUp")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"johndoe\",\"password\":\"password\",\"fullName\":\"John Doe\"}"))
        .andExpect(status().isInternalServerError());
  }
}
