package dev.matheuspereira.fluxcred.application.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.matheuspereira.fluxcred.application.data.request.PersonRequest;
import dev.matheuspereira.fluxcred.application.data.response.PersonResponse;
import dev.matheuspereira.fluxcred.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.domain.model.Person;
import dev.matheuspereira.fluxcred.domain.ports.driver.IPersonService;
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
class PersonControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private IPersonService personService;

  @MockBean private ModelMapper modelMapper;

  @Test
  @WithMockUser(roles = "USER")
  void createPersonShouldReturnCreatedPerson() throws Exception {
    PersonRequest request = new PersonRequest();
    Person person = new Person();
    PersonResponse response = new PersonResponse();

    when(modelMapper.map(request, Person.class)).thenReturn(person);
    when(personService.create(person)).thenReturn(person);
    when(modelMapper.map(person, PersonResponse.class)).thenReturn(response);

    mockMvc
        .perform(post("/people/").contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser(roles = "USER")
  void createPersonShouldReturnBadRequestWhenInputIsInvalid() throws Exception {
    when(modelMapper.map(any(PersonRequest.class), eq(Person.class)))
        .thenThrow(new BusinessException("Invalid input data", 400));

    mockMvc
        .perform(
            post("/people/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"invalidField\":\"value\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "USER")
  void getPersonShouldReturnPerson() throws Exception {
    Person person = new Person();
    PersonResponse response = new PersonResponse();

    when(personService.getById(1)).thenReturn(person);
    when(modelMapper.map(person, PersonResponse.class)).thenReturn(response);

    mockMvc.perform(get("/people/{id}", 1)).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "USER")
  void getPersonShouldReturnNotFoundWhenPersonDoesNotExist() throws Exception {
    when(personService.getById(anyInt())).thenThrow(new BusinessException("Person not found", 404));

    mockMvc.perform(get("/people/{id}", 1)).andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "USER")
  void updatePersonShouldReturnUpdatedPerson() throws Exception {
    PersonRequest request = new PersonRequest();
    Person person = new Person();
    Person updatedPerson = new Person();
    PersonResponse response = new PersonResponse();

    when(modelMapper.map(request, Person.class)).thenReturn(person);
    when(personService.update(1, person)).thenReturn(updatedPerson);
    when(modelMapper.map(updatedPerson, PersonResponse.class)).thenReturn(response);

    mockMvc
        .perform(patch("/people/{id}", 1).contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "USER")
  void updatePersonShouldReturnNotFoundWhenPersonDoesNotExist() throws Exception {
    when(modelMapper.map(any(PersonRequest.class), eq(Person.class))).thenReturn(new Person());
    when(personService.update(anyInt(), any(Person.class)))
        .thenThrow(new BusinessException("Person not found", 404));

    mockMvc
        .perform(
            patch("/people/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\"}"))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deletePersonShouldReturnNoContent() throws Exception {
    doNothing().when(personService).delete(1);

    mockMvc.perform(delete("/people/{id}", 1)).andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deletePersonShouldReturnNotFoundWhenPersonDoesNotExist() throws Exception {
    doThrow(new BusinessException("Person not found", 404)).when(personService).delete(1);

    mockMvc.perform(delete("/people/{id}", 1)).andExpect(status().isNotFound());
  }
}
