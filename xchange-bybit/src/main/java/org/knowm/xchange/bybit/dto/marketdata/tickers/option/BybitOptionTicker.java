package org.knowm.xchange.bybit.dto.marketdata.tickers.option;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;

@SuperBuilder
@Jacksonized
@Value
public class BybitOptionTicker extends BybitTicker {

  @JsonProperty("bid1Iv")
  BigDecimal bid1Iv;

  @JsonProperty("ask1Iv")
  BigDecimal ask1Iv;

  @JsonProperty("markPrice")
  BigDecimal markPrice;

  @JsonProperty("indexPrice")
  BigDecimal indexPrice;

  @JsonProperty("markIv")
  BigDecimal markIv;

  @JsonProperty("underlyingPrice")
  BigDecimal underlyingPrice;

  @JsonProperty("openInterest")
  BigDecimal openInterest;

  @JsonProperty("totalVolume")
  BigDecimal totalVolume;

  @JsonProperty("totalTurnover")
  BigDecimal totalTurnover;

  @JsonProperty("delta")
  BigDecimal delta;

  @JsonProperty("gamma")
  BigDecimal gamma;

  @JsonProperty("vega")
  BigDecimal vega;

  @JsonProperty("theta")
  BigDecimal theta;

  @JsonProperty("predictedDeliveryPrice")
  BigDecimal predictedDeliveryPrice;

  @JsonProperty("change24h")
  BigDecimal change24h;
}
