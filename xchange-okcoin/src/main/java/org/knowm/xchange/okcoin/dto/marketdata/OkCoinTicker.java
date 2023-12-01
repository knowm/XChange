package org.knowm.xchange.okcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class OkCoinTicker {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final BigDecimal last;
  private final BigDecimal vol;
  private final Long contractId;
  private final BigDecimal unitAmount;

  public OkCoinTicker(
      @JsonProperty("high") final BigDecimal high,
      @JsonProperty("low") final BigDecimal low,
      @JsonProperty("buy") final BigDecimal buy,
      @JsonProperty("sell") final BigDecimal sell,
      @JsonProperty("last") final BigDecimal last,
      @JsonProperty("vol") final BigDecimal vol,
      @JsonProperty("contract_id") final Long contractId,
      @JsonProperty("unit_amount") final BigDecimal unitAmount) {

    this.high = high;
    this.low = low;
    this.buy = buy;
    this.sell = sell;
    this.last = last;
    this.vol = vol;
    this.contractId = contractId;
    this.unitAmount = unitAmount;
  }

  /**
   * @return the high
   */
  public BigDecimal getHigh() {

    return high;
  }

  /**
   * @return the low
   */
  public BigDecimal getLow() {

    return low;
  }

  /**
   * @return the buy
   */
  public BigDecimal getBuy() {

    return buy;
  }

  /**
   * @return the sell
   */
  public BigDecimal getSell() {

    return sell;
  }

  /**
   * @return the last
   */
  public BigDecimal getLast() {

    return last;
  }

  /**
   * @return the vol
   */
  public BigDecimal getVol() {

    return vol;
  }

  public Long getContractId() {
    return contractId;
  }

  public BigDecimal getUnitAmount() {
    return unitAmount;
  }

  @Override
  public String toString() {
    return "OkCoinTicker{"
        + "high="
        + high
        + ", low="
        + low
        + ", buy="
        + buy
        + ", sell="
        + sell
        + ", last="
        + last
        + ", vol="
        + vol
        + ", contractId="
        + contractId
        + ", unitAmount="
        + unitAmount
        + '}';
  }
}
