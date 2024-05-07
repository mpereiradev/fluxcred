package dev.matheuspereira.fluxcred.core.domain.model;

import dev.matheuspereira.fluxcred.core.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.core.domain.validator.APValidator;
import dev.matheuspereira.fluxcred.core.domain.validator.CNPJValidator;
import dev.matheuspereira.fluxcred.core.domain.validator.CPFValidator;
import dev.matheuspereira.fluxcred.core.domain.validator.EUValidator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum IdentifierType {
  PF("Pessoa Física", 11) {
    @Override
    public boolean isValid(String identifier) {
      return CPFValidator.isValid(identifier);
    }
  },
  PJ("Pessoa Jurídica", 14) {
    @Override
    public boolean isValid(String identifier) {
      return CNPJValidator.isValid(identifier);
    }
  },
  EU("Estudante Universitário", 8) {
    @Override
    public boolean isValid(String identifier) {
      return EUValidator.isValid(identifier);
    }
  },
  AP("Aposentado", 10) {
    @Override
    public boolean isValid(String identifier) {
      return APValidator.isValid(identifier);
    }
  };

  private String description;
  private int length;

  public abstract boolean isValid(String identifier);

  IdentifierType(String description, int length) {
    this.description = description;
    this.length = length;
  }

  public static IdentifierType fromLength(int length) {
    return Arrays.stream(IdentifierType.values()).filter(i -> i.getLength() == length).findAny()
        .orElseThrow(() -> new BusinessException("No valid identifier type found for length: " + length, 412));
  }

  public static IdentifierType fromKey(String key) {
    for (IdentifierType type : IdentifierType.values()) {
      if (type.name().equalsIgnoreCase(key)) {
        return type;
      }
    }

    throw new BusinessException("No valid identifier type found for key: " + key, 412);
  }
}