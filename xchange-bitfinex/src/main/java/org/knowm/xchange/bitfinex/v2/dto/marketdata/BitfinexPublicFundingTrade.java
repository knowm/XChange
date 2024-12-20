package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"tradeId", "timestamp", "amount", "rate", "period"})
@Getter
@ToString
public class BitfinexPublicFundingTrade {

  private long tradeId;
  private long timestamp;
  private BigDecimal amount;
  private BigDecimal rate;
  private int period;

  public BitfinexPublicFundingTrade() {}

  public BitfinexPublicFundingTrade(
      long tradeId, long timestamp, BigDecimal amount, BigDecimal rate, int period) {

    this.tradeId = tradeId;
    this.timestamp = timestamp;
    this.amount = amount;
    this.rate = rate;
    this.period = period;
  }
}
