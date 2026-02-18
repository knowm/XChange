package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.instrument.Instrument;

@Getter
@ToString
public class BinanceFundingRateInfo {
  private final Instrument instrument;
  private final BigDecimal adjustedFundingRateCap;
  private final BigDecimal adjustedFundingRateFloor;
  private final int fundingIntervalHours;

  public BinanceFundingRateInfo(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("adjustedFundingRateCap") BigDecimal adjustedFundingRateCap,
      @JsonProperty("adjustedFundingRateFloor") BigDecimal adjustedFundingRateFloor,
      @JsonProperty("fundingIntervalHours") int fundingIntervalHours) {
    this.instrument = BinanceAdapters.adaptSymbol(symbol, true);
    this.adjustedFundingRateCap = adjustedFundingRateCap;
    this.adjustedFundingRateFloor = adjustedFundingRateFloor;
    this.fundingIntervalHours = fundingIntervalHours;
  }
}
