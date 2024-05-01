package dev.matheuspereira.fluxcred.domain.validator;

import dev.matheuspereira.fluxcred.domain.model.IdentifierType;

public class IdentifierValidator {

  public static boolean validate(String identifier, IdentifierType type) {
    switch (type) {
      case EU:
        return validateEU(identifier);
      case AP:
        return validateAP(identifier);
      case PF:
        return validateCPF(identifier);
      case PJ:
        return validateCNPJ(identifier);
      default:
        return false;
    }
  }

  private static boolean validateEU(String identifier) {
    try {
      int firstDigit = Character.getNumericValue(identifier.charAt(0));
      int lastDigit = Character.getNumericValue(identifier.charAt(identifier.length() - 1));
      return identifier.length() == 8 && (firstDigit + lastDigit) == 9;
    } catch (Exception e) {
      return false;
    }
  }

  private static boolean validateAP(String identifier) {
    try {
      char lastDigit = identifier.charAt(identifier.length() - 1);
      for (int i = 0; i < identifier.length() - 1; i++) {
        if (identifier.charAt(i) == lastDigit) {
          return false;
        }
      }
      return identifier.length() == 10;
    } catch (Exception e) {
      return false;
    }
  }

  private static boolean validateCPF(String cpf) {
    return CPFValidator.isValid(cpf);
  }

  private static boolean validateCNPJ(String cnpj) {
    return CNPJValidator.isValid(cnpj);
  }
}

