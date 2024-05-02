package dev.matheuspereira.fluxcred.core.application.web;

import dev.matheuspereira.fluxcred.core.application.data.request.LoanConfigRequest;
import dev.matheuspereira.fluxcred.core.application.data.response.LoanConfigResponse;
import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import dev.matheuspereira.fluxcred.core.domain.model.LoanConfig;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.ILoanConfigService;
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
@RolesAllowed("ADMIN")
@RequiredArgsConstructor
@RequestMapping("/loan-configs")
@Tag(name = "Loan Configurations", description = "API for managing loan configurations")
public class LoanConfigController {
  private final ILoanConfigService loanConfigService;
  private final ModelMapper modelMapper;

  @Operation(summary = "Create a new loan configuration", description = "Registers new loan configuration settings")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Loan configuration successfully created"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")
  })
  @PostMapping("/")
  public ResponseEntity<LoanConfigResponse> createLoanConfig(@RequestBody LoanConfigRequest loanConfigRequest) {
    LoanConfig config = modelMapper.map(loanConfigRequest, LoanConfig.class);
    config = loanConfigService.create(config);
    LoanConfigResponse configResponse = modelMapper.map(config, LoanConfigResponse.class);
    return ResponseEntity.status(201).body(configResponse);
  }

  @Operation(
      summary = "Get loan configuration by Identifier Type",
      description = "Returns loan configuration details for a given identifier type")
  @GetMapping("/{identifierType}")
  public ResponseEntity<LoanConfigResponse> getLoanConfig(
      @PathVariable IdentifierType identifierType) {
    LoanConfig config = loanConfigService.getByIdentifierType(identifierType);
    LoanConfigResponse configResponse = modelMapper.map(config, LoanConfigResponse.class);
    return ResponseEntity.ok(configResponse);
  }

  @Operation(summary = "Update loan configuration", description = "Updates the loan configuration for a given identifier type")
  @PatchMapping("/{id}")
  public ResponseEntity<LoanConfigResponse> updateLoanConfig(@PathVariable Integer id, @RequestBody LoanConfigRequest loanConfigRequest) {
    LoanConfig config = modelMapper.map(loanConfigRequest, LoanConfig.class);
    config = loanConfigService.update(id, config);
    LoanConfigResponse configResponse = modelMapper.map(config, LoanConfigResponse.class);
    return ResponseEntity.ok(configResponse);
  }

  @Operation(summary = "Delete loan configuration", description = "Deletes the loan configuration for a given identifier type")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLoanConfig(@PathVariable Integer id) {
    loanConfigService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
