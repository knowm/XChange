package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.instrument.Instrument;

@Getter
@ToString
public class BinanceFundingRateHistory {

  private final Instrument instrument;
  private final BigDecimal fundingRate;
  private final Instant fundingTime;
  private final BigDecimal markPrice;

  public BinanceFundingRateHistory(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("fundingRate") BigDecimal fundingRate,
      @JsonProperty("fundingTime") long fundingTime,
      @JsonProperty("markPrice") BigDecimal markPrice) {
    this.instrument = BinanceAdapters.adaptSymbol(symbol, true);
    this.fundingRate = fundingRate;
    this.fundingTime = Instant.ofEpochMilli(fundingTime);
    this.markPrice = markPrice;
  }
}
