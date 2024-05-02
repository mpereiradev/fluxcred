package dev.matheuspereira.fluxcred.application.web;

import dev.matheuspereira.fluxcred.application.data.request.LoanRequest;
import dev.matheuspereira.fluxcred.application.data.response.LoanResponse;
import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.domain.ports.driver.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@Tag(name = "Loan Management", description = "APIs for managing loans")
public class LoanController {

  private final ILoanService loanService;
  private final ModelMapper modelMapper;

  @PostMapping
  @Operation(summary = "Create a new loan")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Loan created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
      })
  public ResponseEntity<LoanResponse> createLoan(@RequestBody LoanRequest request) {
    var loan = modelMapper.map(request, Loan.class);
    loan = loanService.create(loan);
    var response = modelMapper.map(loan, LoanResponse.class);
    return ResponseEntity.status(201).body(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a loan by ID")
  public ResponseEntity<LoanResponse> getLoan(@PathVariable Integer id) {
    var loan = loanService.getById(id);
    var response = modelMapper.map(loan, LoanResponse.class);
    return ResponseEntity.ok(response);
  }

  @RolesAllowed("ADMIN")
  @PatchMapping("/{id}")
  @Operation(summary = "Update a loan")
  public ResponseEntity<LoanResponse> updateLoan(
      @PathVariable Integer id, @RequestBody LoanRequest request) {
    var loanPatch = modelMapper.map(request, Loan.class);
    var loan = loanService.update(id, loanPatch);
    var response = modelMapper.map(loan, LoanResponse.class);
    return ResponseEntity.ok(response);
  }

  @RolesAllowed("ADMIN")
  @DeleteMapping("/{id}")
  @Operation(summary = "Cancel a loan")
  public ResponseEntity<LoanResponse> cancelLoan(@PathVariable Integer id) {
    var loan = loanService.cancel(id);
    var response = modelMapper.map(loan, LoanResponse.class);
    return ResponseEntity.ok(response);
  }
}
