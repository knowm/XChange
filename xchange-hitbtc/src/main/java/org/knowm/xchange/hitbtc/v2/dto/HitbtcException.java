package org.knowm.xchange.hitbtc.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class HitbtcException extends RuntimeException {

  HitbtcError hitbtcError;

  public HitbtcException(@JsonProperty("error") HitbtcError hitbtcError) {

    super(hitbtcError.getMessage());
  }

}
