package dev.matheuspereira.fluxcred.application.web;

import dev.matheuspereira.fluxcred.application.data.request.PersonRequest;
import dev.matheuspereira.fluxcred.application.data.response.PersonResponse;
import dev.matheuspereira.fluxcred.domain.model.Person;
import dev.matheuspereira.fluxcred.domain.ports.driver.IPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/people")
@Tag(name = "People", description = "API for managing people")
public class PersonController {
  private final IPersonService personService;
  private final ModelMapper modelMapper;

  @Operation(summary = "Create a new person", description = "Registers a new person in the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Person successfully created"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")
  })
  @PostMapping("/")
  public ResponseEntity<PersonResponse> createPerson(@RequestBody PersonRequest personRequest) {
    Person person = modelMapper.map(personRequest, Person.class);
    person = personService.create(person);

    PersonResponse personResponse = modelMapper.map(person, PersonResponse.class);
    return ResponseEntity.status(201).body(personResponse);
  }

  @Operation(summary = "Get a person by ID", description = "Returns a person's details")
  @GetMapping("/{id}")
  public ResponseEntity<PersonResponse> getPerson(@PathVariable Integer id) {
    Person person = personService.getById(id);
    PersonResponse personResponse = modelMapper.map(person, PersonResponse.class);
    return ResponseEntity.ok(personResponse);
  }

  @Operation(summary = "Update a person", description = "Updates the specified person's details")
  @PatchMapping("/{id}")
  public ResponseEntity<PersonResponse> updatePerson(@PathVariable Integer id, @RequestBody PersonRequest personRequest) {
    Person person = modelMapper.map(personRequest, Person.class);

    person = personService.update(id, person);
    PersonResponse personResponse = modelMapper.map(person, PersonResponse.class);
    return ResponseEntity.ok(personResponse);
  }

  @Operation(summary = "Delete a person", description = "Deletes the specified person")
  @DeleteMapping("/{id}")
  @RolesAllowed("ADMIN")
  public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
    personService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
