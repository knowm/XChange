package com.xeiam.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyMetaData {
  @JsonProperty
  public int scale;

  public CurrencyMetaData() {
  }

  public CurrencyMetaData(int scale) {
    this.scale = scale;
  }
}
