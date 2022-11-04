package org.knowm.xchange.okex.v5.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OkexTicker {
  @JsonProperty("instType")
  private String instrumentType;

  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("last")
  private BigDecimal last;

  @JsonProperty("lastSz")
  private BigDecimal lastSize;

  @JsonProperty("askPx")
  private BigDecimal askPrice;

  @JsonProperty("askSz")
  private BigDecimal askSize;

  @JsonProperty("bidPx")
  private BigDecimal bidPrice;

  @JsonProperty("bidSz")
  private BigDecimal bidSize;

  @JsonProperty("open24h")
  private BigDecimal open24h;

  @JsonProperty("high24h")
  private BigDecimal high24h;

  @JsonProperty("low24h")
  private BigDecimal low24h;

  /**
   * 24h trading volume, with a unit of currency. If it is a derivatives contract, the value is the
   * number of base currency. If it is SPOT/MARGIN, the value is the number of quote currency.
   */
  @JsonProperty("volCcy24h")
  private BigDecimal volumeCurrency24h;

  /**
   * 24h trading volume, with a unit of contact. If it is a derivatives contract, the value is the
   * number of contracts. If it is SPOT/MARGIN, the value is the amount of base currency.
   */
  @JsonProperty("vol24h")
  private BigDecimal volume24h;

  @JsonProperty("sodUtc0")
  private String sodUtc0;

  @JsonProperty("sodUtc8")
  private String sodUtc8;

  @JsonProperty("ts")
  private Date timestamp;
}
