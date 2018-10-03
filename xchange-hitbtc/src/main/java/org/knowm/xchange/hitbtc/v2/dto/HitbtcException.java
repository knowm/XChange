package org.knowm.xchange.hitbtc.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcException extends RuntimeException {

  private HitbtcError hitbtcError;

  public HitbtcException(@JsonProperty("error") HitbtcError hitbtcError) {

    super(hitbtcError.getMessage());
    this.hitbtcError = hitbtcError;
  }

  public HitbtcError getHitbtcError() {
    return hitbtcError;
  }
}
