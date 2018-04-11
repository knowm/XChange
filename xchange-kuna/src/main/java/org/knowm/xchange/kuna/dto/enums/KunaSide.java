package org.knowm.xchange.kuna.dto.enums;

/** @author Dat Bui */
public enum KunaSide {
  BUY,
  SELL;

  public static KunaSide valueOfIgnoreCase(String name) {
    return name == null ? null : KunaSide.valueOf(name.trim().toUpperCase());
  }
}
