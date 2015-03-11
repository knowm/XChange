package com.xeiam.xchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyMetaData {
  @JsonProperty("scale")
  public final int scale;

  public CurrencyMetaData(int scale) {
    this.scale = scale;
  }
}
