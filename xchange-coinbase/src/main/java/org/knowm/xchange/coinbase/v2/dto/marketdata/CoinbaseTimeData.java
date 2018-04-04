package org.knowm.xchange.coinbase.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseTimeData {

  private CoinbaseTime data;

  public CoinbaseTime getData() {
    return data;
  }

  public void setData(CoinbaseTime data) {
    this.data = data;
  }

  public static class CoinbaseTime {

    private final BigDecimal epoch;
    private final String iso;

    @JsonCreator
    public CoinbaseTime(@JsonProperty("iso") String iso, @JsonProperty("epoch") BigDecimal epoch) {
      this.epoch = epoch;
      this.iso = iso;
    }

    public String getIso() {
      return iso;
    }

    public BigDecimal getEpoch() {
      return epoch;
    }

    @Override
    public String toString() {
      return iso;
    }
  }
}
