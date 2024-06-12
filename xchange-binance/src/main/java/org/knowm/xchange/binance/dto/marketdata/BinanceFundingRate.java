package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.instrument.Instrument;

@Getter
@ToString
public class BinanceFundingRate {

  private final Instrument instrument;
  private final BigDecimal markPrice;
  private final BigDecimal indexPrice;
  private final BigDecimal estimatedSettlePrice;
  private final BigDecimal lastFundingRate;
  private final Date nextFundingTime;
  private final BigDecimal interestRate;
  private final Date time;

  public BinanceFundingRate(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("markPrice") BigDecimal markPrice,
      @JsonProperty("indexPrice") BigDecimal indexPrice,
      @JsonProperty("estimatedSettlePrice") BigDecimal estimatedSettlePrice,
      @JsonProperty("lastFundingRate") BigDecimal lastFundingRate,
      @JsonProperty("nextFundingTime") Date nextFundingTime,
      @JsonProperty("interestRate") BigDecimal interestRate,
      @JsonProperty("time") Date time) {
    this.instrument = BinanceAdapters.adaptSymbol(symbol, true);
    this.markPrice = markPrice;
    this.indexPrice = indexPrice;
    this.estimatedSettlePrice = estimatedSettlePrice;
    this.lastFundingRate = lastFundingRate;
    this.nextFundingTime = nextFundingTime;
    this.interestRate = interestRate;
    this.time = time;
  }
}
