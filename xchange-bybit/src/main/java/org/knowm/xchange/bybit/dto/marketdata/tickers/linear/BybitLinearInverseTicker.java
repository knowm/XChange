package org.knowm.xchange.bybit.dto.marketdata.tickers.linear;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;

@SuperBuilder
@Jacksonized
@Value
public class BybitLinearInverseTicker extends BybitTicker {

  @JsonProperty("indexPrice")
  BigDecimal indexPrice;

  @JsonProperty("markPrice")
  BigDecimal markPrice;

  @JsonProperty("prevPrice1h")
  BigDecimal prevPrice1h;

  @JsonProperty("prevPrice24h")
  BigDecimal prevPrice24h;

  @JsonProperty("price24hPcnt")
  BigDecimal price24hPcnt;

  @JsonProperty("openInterest")
  BigDecimal openInterest;

  @JsonProperty("openInterestValue")
  BigDecimal openInterestValue;

  @JsonProperty("fundingRate")
  BigDecimal fundingRate;

  @JsonProperty("nextFundingTime")
  Date nextFundingTime;

  @JsonProperty("predictedDeliveryPrice")
  BigDecimal predictedDeliveryPrice;

  @JsonProperty("basisRate")
  BigDecimal basisRate;

  @JsonProperty("basis")
  BigDecimal basis;

  @JsonProperty("deliveryFeeRate")
  BigDecimal deliveryFeeRate;

  @JsonProperty("deliveryTime")
  Date deliveryTime;
}
