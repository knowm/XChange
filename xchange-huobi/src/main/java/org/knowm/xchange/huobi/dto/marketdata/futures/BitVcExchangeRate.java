package org.knowm.xchange.huobi.dto.marketdata.futures;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcExchangeRate {
  private final BigDecimal rate;

  public BitVcExchangeRate(@JsonProperty("rate") BigDecimal rate) {
    this.rate = rate;
  }

  public BigDecimal getRate() {

    return rate;
  }

}
