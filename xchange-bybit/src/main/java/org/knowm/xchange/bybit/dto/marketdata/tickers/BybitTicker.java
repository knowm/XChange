package org.knowm.xchange.bybit.dto.marketdata.tickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public abstract class BybitTicker {

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("lastPrice")
  BigDecimal lastPrice;

  @JsonProperty("bid1Price")
  BigDecimal bid1Price;

  @JsonProperty("bid1Size")
  BigDecimal bid1Size;

  @JsonProperty("ask1Price")
  BigDecimal ask1Price;

  @JsonProperty("ask1Size")
  BigDecimal ask1Size;

  @JsonProperty("highPrice24h")
  BigDecimal highPrice24h;

  @JsonProperty("lowPrice24h")
  BigDecimal lowPrice24h;

  @JsonProperty("turnover24h")
  BigDecimal turnover24h;

  @JsonProperty("volume24h")
  BigDecimal volume24h;
}
