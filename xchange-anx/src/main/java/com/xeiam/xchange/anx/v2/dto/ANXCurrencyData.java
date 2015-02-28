package com.xeiam.xchange.anx.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ANXCurrencyData {
  @JsonProperty("scale")
  public int scale;

  public ANXCurrencyData() {
  }

  public ANXCurrencyData(int scale) {
    this.scale = scale;
  }
}
