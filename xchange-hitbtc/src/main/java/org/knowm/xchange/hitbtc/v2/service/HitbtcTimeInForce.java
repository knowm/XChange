package org.knowm.xchange.hitbtc.v2.service;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum HitbtcTimeInForce {
  GTC,
  FOK,
  IOC;

  @JsonCreator
  public static HitbtcTimeInForce getTimeInForce(String s) {
    try {
      return HitbtcTimeInForce.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown ordtime in force " + s + ".");
    }
  }
}
