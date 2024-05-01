package dev.matheuspereira.fluxcred.domain.validator;

public class CNPJValidator {
  public static boolean isValid(String cnpj) {
    cnpj = cnpj.replaceAll("\\D", "");
    if (cnpj.length() != 14 || cnpj.matches(cnpj.charAt(0) + "{14}")) return false;

    int[] weights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};


    int sum = 0;
    for (int i = 0; i < 12; i++) {
      sum += Integer.parseInt(cnpj.substring(i, i + 1)) * weights[i];
    }
    int remaining = sum % 11;
    String firstCheckDigit = (remaining < 2) ? "0" : Integer.toString(11 - remaining);

    weights = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    sum = 0;
    for (int i = 0; i < 13; i++) {
      sum += Integer.parseInt(cnpj.substring(i, i + 1)) * weights[i];
    }
    remaining = sum % 11;
    String secondCheckDigit = (remaining < 2) ? "0" : Integer.toString(11 - remaining);

    return cnpj.equals(cnpj.substring(0, 12) + firstCheckDigit + secondCheckDigit);
  }
}

