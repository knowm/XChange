package org.knowm.xchange.okex.v5.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkexPriceLimit {

  private final String instType;
  private final String instId;
  private final BigDecimal buyLmt;
  private final BigDecimal sellLmt;
  private final Date ts;

  public OkexPriceLimit(
      @JsonProperty("instType") String instType,
      @JsonProperty("instId") String instId,
      @JsonProperty("buyLmt") BigDecimal buyLmt,
      @JsonProperty("sellLmt") BigDecimal sellLmt,
      @JsonProperty("ts") Date ts) {
    this.instType = instType;
    this.instId = instId;
    this.buyLmt = buyLmt;
    this.sellLmt = sellLmt;
    this.ts = ts;
  }

  public String getInstType() {
    return instType;
  }

  public String getInstId() {
    return instId;
  }

  public BigDecimal getBuyLmt() {
    return buyLmt;
  }

  public BigDecimal getSellLmt() {
    return sellLmt;
  }

  public Date getTs() {
    return ts;
  }

  @Override
  public String toString() {
    return "OkexPriceLimit{"
        + "instType='"
        + instType
        + '\''
        + ", instId="
        + instId
        + ", buyLmt="
        + buyLmt
        + ", sellLmt="
        + sellLmt
        + ", ts="
        + ts
        + '}';
  }
}
