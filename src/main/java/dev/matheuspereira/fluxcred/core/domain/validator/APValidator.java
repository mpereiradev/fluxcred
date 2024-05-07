package dev.matheuspereira.fluxcred.core.domain.validator;

public class APValidator {
  public static boolean isValid(String identifier) {
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
}
