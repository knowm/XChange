package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitfinexFundingTradeResponse {

  private final BigDecimal rate;
  private final BigDecimal period;
  private final BigDecimal amount;
  private final BigDecimal timestamp;
  private final String type;
  private final String tradeId;
  private final String offerId;

  /**
   * Constructor
   *
   * @param rate
   * @param amount
   * @param timestamp
   * @param period
   * @param type
   * @param tradeId
   * @param offerId
   */
  public BitfinexFundingTradeResponse(
      @JsonProperty("rate") final BigDecimal rate,
      @JsonProperty("period") final BigDecimal period,
      @JsonProperty("amount") final BigDecimal amount,
      @JsonProperty("timestamp") final BigDecimal timestamp,
      @JsonProperty("type") final String type,
      @JsonProperty("tid") final String tradeId,
      @JsonProperty("offer_id") final String offerId) {

    this.rate = rate;
    this.amount = amount;
    this.period = period;
    this.timestamp = timestamp;
    this.type = type;
    this.tradeId = tradeId;
    this.offerId = offerId;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getPeriod() {
    return period;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getTimestamp() {
    return timestamp;
  }

  public String getType() {
    return type;
  }

  public String getTradeId() {
    return tradeId;
  }

  public String getOfferId() {
    return offerId;
  }

  @Override
  public String toString() {
    return "BitfinexFundingTradeResponse [rate="
        + rate
        + ", period="
        + period
        + ", amount="
        + amount
        + ", timestamp="
        + timestamp
        + ", type="
        + type
        + ", tradeId="
        + tradeId
        + ", offerId="
        + offerId
        + "]";
  }
}
