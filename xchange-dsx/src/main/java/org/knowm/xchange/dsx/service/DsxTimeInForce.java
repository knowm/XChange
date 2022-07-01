package org.knowm.xchange.dsx.service;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DsxTimeInForce {
  GTC,
  FOK,
  IOC;

  @JsonCreator
  public static DsxTimeInForce getTimeInForce(String s) {
    try {
      return DsxTimeInForce.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown ordtime in force " + s + ".");
    }
  }
}
