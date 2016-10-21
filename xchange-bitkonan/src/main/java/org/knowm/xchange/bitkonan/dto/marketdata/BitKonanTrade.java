package org.knowm.xchange.bitkonan.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitKonanTrade {
  private final BigDecimal total;
  private final BigDecimal btc;
  private final BigDecimal usd;
  private final Date time;

  public BitKonanTrade(@JsonProperty("total") BigDecimal total, @JsonProperty("btc") BigDecimal btc, @JsonProperty("usd") BigDecimal usd,
      @JsonProperty("time") Date time) {
    this.total = total;
    this.btc = btc;
    this.usd = usd;
    this.time = time;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public BigDecimal getBtc() {
    return btc;
  }

  public BigDecimal getUsd() {
    return usd;
  }

  public Date getTime() {
    return time;
  }
}
