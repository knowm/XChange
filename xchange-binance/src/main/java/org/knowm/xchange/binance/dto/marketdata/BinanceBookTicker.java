package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.dto.marketdata.Ticker;

@Getter
public final class BinanceBookTicker {

  @Setter
  public long updateId;
  private final BigDecimal bidPrice;
  private final BigDecimal bidQty;
  private final BigDecimal askPrice;
  private final BigDecimal askQty;
  private final String symbol;
  private final long eventTime;
  private final long transactionTime;
  // The cached ticker
  private Ticker ticker;

  public BinanceBookTicker(
      @JsonProperty("bidPrice") BigDecimal bidPrice,
      @JsonProperty("bidQty") BigDecimal bidQty,
      @JsonProperty("askPrice") BigDecimal askPrice,
      @JsonProperty("askQty") BigDecimal askQty,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("E") long eventTime,
      @JsonProperty("T") long transactionTime) {
    this.bidPrice = bidPrice;
    this.bidQty = bidQty;
    this.askPrice = askPrice;
    this.askQty = askQty;
    this.symbol = symbol;
    this.eventTime = eventTime;
    this.transactionTime = transactionTime;
  }

  public synchronized Ticker toTicker(boolean isFuture) {
    if (ticker == null) {
      ticker =
          new Ticker.Builder()
              .instrument(BinanceAdapters.adaptSymbol(symbol, isFuture))
              .ask(askPrice)
              .bid(bidPrice)
              .askSize(askQty)
              .bidSize(bidQty)
              .timestamp(new Date(transactionTime))
              .build();
    }
    return ticker;
  }
}
