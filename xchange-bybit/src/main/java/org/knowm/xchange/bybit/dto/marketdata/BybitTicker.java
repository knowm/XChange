package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;

@Builder
@Jacksonized
@Data
public class BybitTicker {

  @JsonProperty("symbol")
  String symbol;

  CurrencyPair currencyPair;

  @JsonProperty("bid1Price")
  BigDecimal bestBidPrice;

  @JsonProperty("bid1Size")
  BigDecimal bestBidSize;

  @JsonProperty("ask1Price")
  BigDecimal bestAskPrice;

  @JsonProperty("ask1Size")
  BigDecimal bestAskSize;

  @JsonProperty("lastPrice")
  BigDecimal lastPrice;

  @JsonProperty("prevPrice24h")
  BigDecimal prevPrice24h;

  @JsonProperty("price24hPcnt")
  BigDecimal price24hPercentageChange;

  @JsonProperty("highPrice24h")
  BigDecimal highPrice;

  @JsonProperty("lowPrice24h")
  BigDecimal lowPrice;

  @JsonProperty("turnover24h")
  BigDecimal turnover24h;

  @JsonProperty("volume24h")
  BigDecimal volume24h;

  @JsonProperty("usdIndexPrice")
  BigDecimal usdIndexPrice;

}
