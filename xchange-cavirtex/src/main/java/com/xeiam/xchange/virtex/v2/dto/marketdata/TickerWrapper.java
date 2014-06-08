package com.xeiam.xchange.virtex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public final class TickerWrapper {

  private VirtExTicker ticker;

  @JsonAnySetter
  public void parseUnknownProperties(String propertyName, VirtExTicker propertyValue) {

    this.ticker = propertyValue;
  }

  public VirtExTicker getTicker() {

    return ticker;
  }

}