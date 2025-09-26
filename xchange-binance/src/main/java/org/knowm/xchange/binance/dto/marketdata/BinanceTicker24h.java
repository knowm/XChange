package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public final class BinanceTicker24h {

  private BigDecimal priceChange;
  private BigDecimal priceChangePercent;
  private BigDecimal weightedAvgPrice;
  private BigDecimal prevClosePrice;
  private BigDecimal lastPrice;
  private BigDecimal lastQty;
  private BigDecimal bidPrice;
  private BigDecimal bidQty;
  private BigDecimal askPrice;
  private BigDecimal askQty;
  private BigDecimal openPrice;
  private BigDecimal highPrice;
  private BigDecimal lowPrice;
  private BigDecimal volume;
  private BigDecimal quoteVolume;
  private long openTime;
  private long closeTime;
  private long firstId;
  private long lastId;
  private long count;

  private String symbol;

  // filter out COIN-M futures and inverse
  public boolean isValid() {
    return !(symbol.contains("_") || symbol.endsWith("USD"));
  }
}
