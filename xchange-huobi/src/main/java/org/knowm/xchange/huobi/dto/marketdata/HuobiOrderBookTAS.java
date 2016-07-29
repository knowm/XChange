package org.knowm.xchange.huobi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Order book and TAS(Times and sales)
 */
public class HuobiOrderBookTAS {

  /**
   * Times and sales.
   */
  private final HuobiTradeObject[] trades;

  /**
   * Latest.
   */
  private final BigDecimal pNew;

  /**
   * Percent change.
   */
  private final BigDecimal level;

  /**
   * Volume.
   */
  private final BigDecimal amount;

  /**
   * Total(RMB).
   */
  private final BigDecimal total;

  private final BigDecimal amp;

  /**
   * Open.
   */
  private final BigDecimal pOpen;

  /**
   * High.
   */
  private final BigDecimal pHigh;

  /**
   * Low.
   */
  private final BigDecimal pLow;

  /**
   * Close.
   */
  private final BigDecimal pLast;

  public HuobiOrderBookTAS(@JsonProperty("trades") final HuobiTradeObject[] trades, @JsonProperty("p_new") final BigDecimal pNew,
      @JsonProperty("level") final BigDecimal level, @JsonProperty("amount") final BigDecimal amount, @JsonProperty("total") final BigDecimal total,
      @JsonProperty("amp") final BigDecimal amp, @JsonProperty("p_open") final BigDecimal pOpen, @JsonProperty("p_high") final BigDecimal pHigh,
      @JsonProperty("p_low") final BigDecimal pLow, @JsonProperty("p_last") final BigDecimal pLast) {

    this.trades = trades;
    this.pNew = pNew;
    this.level = level;
    this.amount = amount;
    this.total = total;
    this.amp = amp;
    this.pOpen = pOpen;
    this.pHigh = pHigh;
    this.pLow = pLow;
    this.pLast = pLast;
  }

  public HuobiTradeObject[] getTrades() {

    return trades;
  }

  public BigDecimal getPNew() {

    return pNew;
  }

  public BigDecimal getLevel() {

    return level;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getTotal() {

    return total;
  }

  public BigDecimal getAmp() {

    return amp;
  }

  public BigDecimal getPOpen() {

    return pOpen;
  }

  public BigDecimal getPHigh() {

    return pHigh;
  }

  public BigDecimal getPLow() {

    return pLow;
  }

  public BigDecimal getPLast() {

    return pLast;
  }
}
