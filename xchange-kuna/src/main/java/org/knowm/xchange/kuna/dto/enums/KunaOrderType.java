package org.knowm.xchange.kuna.dto.enums;

/** @author Dat Bui */
public enum KunaOrderType {
  LIMIT,
  MARKET;

  public static KunaOrderType valueOfIgnoreCase(String name) {
    return name == null ? null : KunaOrderType.valueOf(name.trim().toUpperCase());
  }
}
