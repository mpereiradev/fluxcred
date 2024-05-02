package dev.matheuspereira.fluxcred.core.domain.validator;

import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentifierValidatorTest {

  @Test
  void validEUIdentifierReturnsTrue() {
    assertTrue(IdentifierValidator.validate("42311815", IdentifierType.EU));  // A soma de 4 + 5 é 9
  }

  @Test
  void invalidEUIdentifierReturnsFalse() {
    assertFalse(IdentifierValidator.validate("32423123", IdentifierType.EU)); // A soma de 1 + 3 não é 9
  }

  @Test
  void invalidEUIdentifierLongerLengthReturnsFalse() {
    assertFalse(IdentifierValidator.validate("421311819", IdentifierType.EU)); // Tem mais de 8 caracteres
  }

  @Test
  void validAPIdentifierReturnsTrue() {
    assertTrue(IdentifierValidator.validate("1234567890", IdentifierType.AP)); // 0 não está nos outros dígitos
  }

  @Test
  void invalidAPIdentifierReturnsFalse() {
    assertFalse(IdentifierValidator.validate("1923456789", IdentifierType.AP)); // 9 está presente nos outros dígitos
  }

  @Test
  void invalidAPIdentifierLongerLengthReturnsFalse() {
    assertFalse(IdentifierValidator.validate("42131181905", IdentifierType.AP)); // Tem mais de 10 caracteres
  }

  @Test
  void validCPFIdentifierReturnsTrue() {
    assertTrue(IdentifierValidator.validate("52998224725", IdentifierType.PF)); // CPF válido
  }

  @Test
  void invalidCPFIdentifierReturnsFalse() {
    assertFalse(IdentifierValidator.validate("52998224726", IdentifierType.PF)); // CPF inválido
  }

  @Test
  void validCNPJIdentifierReturnsTrue() {
    assertTrue(IdentifierValidator.validate("17750412000141", IdentifierType.PJ)); // CNPJ válido
  }

  @Test
  void invalidCNPJIdentifierReturnsFalse() {
    assertFalse(IdentifierValidator.validate("36200422000177", IdentifierType.PJ)); // CNPJ inválido
  }
}
