package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;

@Getter
public final class BinanceBookTicker {

  @Setter public long updateId;
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

  /**
   * Resolves the instrument from the ticker symbol through the symbol mapping.
   * If not mapped, the instrument is null for spot and has a null currency pair for futures.
   */
  public synchronized Ticker toTicker(boolean isFuture) {
    return toTicker(BinanceAdapters.adaptSymbol(symbol, isFuture));
  }

  /**
   * Uses the given instrument instead of resolving the symbol through the symbol mapping.
   * The instrument must correspond to the ticker symbol; this is not validated.
   */
  public synchronized Ticker toTicker(Instrument instrument) {
    if (ticker == null || !Objects.equals(ticker.getInstrument(), instrument)) {
      ticker =
          new Ticker.Builder()
              .instrument(instrument)
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
