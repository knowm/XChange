package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;

@Getter
public final class BinanceTicker24h {

  private final BigDecimal priceChange;
  private final BigDecimal priceChangePercent;
  private final BigDecimal weightedAvgPrice;
  private final BigDecimal prevClosePrice;
  private final BigDecimal lastPrice;
  private final BigDecimal lastQty;
  private final BigDecimal bidPrice;
  private final BigDecimal bidQty;
  private final BigDecimal askPrice;
  private final BigDecimal askQty;
  private final BigDecimal openPrice;
  private final BigDecimal highPrice;
  private final BigDecimal lowPrice;
  private final BigDecimal volume;
  private final BigDecimal quoteVolume;
  private final long openTime;
  private final long closeTime;
  private final long firstId;
  private final long lastId;
  private final long count;
  private final String symbol;

  // The curency pair that is unfortunately not returned in the response
  private Instrument pair;

  // The cached ticker
  private Ticker ticker;

  public BinanceTicker24h(
      @JsonProperty("priceChange") BigDecimal priceChange,
      @JsonProperty("priceChangePercent") BigDecimal priceChangePercent,
      @JsonProperty("weightedAvgPrice") BigDecimal weightedAvgPrice,
      @JsonProperty("prevClosePrice") BigDecimal prevClosePrice,
      @JsonProperty("lastPrice") BigDecimal lastPrice,
      @JsonProperty("lastQty") BigDecimal lastQty,
      @JsonProperty("bidPrice") BigDecimal bidPrice,
      @JsonProperty("bidQty") BigDecimal bidQty,
      @JsonProperty("askPrice") BigDecimal askPrice,
      @JsonProperty("askQty") BigDecimal askQty,
      @JsonProperty("openPrice") BigDecimal openPrice,
      @JsonProperty("highPrice") BigDecimal highPrice,
      @JsonProperty("lowPrice") BigDecimal lowPrice,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("quoteVolume") BigDecimal quoteVolume,
      @JsonProperty("openTime") long openTime,
      @JsonProperty("closeTime") long closeTime,
      @JsonProperty("firstId") long firstId,
      @JsonProperty("lastId") long lastId,
      @JsonProperty("count") long count,
      @JsonProperty("symbol") String symbol) {
    this.priceChange = priceChange;
    this.priceChangePercent = priceChangePercent;
    this.weightedAvgPrice = weightedAvgPrice;
    this.prevClosePrice = prevClosePrice;
    this.lastPrice = lastPrice;
    this.lastQty = lastQty;
    this.bidPrice = bidPrice;
    this.bidQty = bidQty;
    this.askPrice = askPrice;
    this.askQty = askQty;
    this.openPrice = openPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.volume = volume;
    this.quoteVolume = quoteVolume;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.firstId = firstId;
    this.lastId = lastId;
    this.count = count;
    this.symbol = symbol;
  }
  public void setInstrument(Instrument pair) {
    this.pair = pair;
  }

  public synchronized Ticker toTicker(boolean isFuture) {
    Instrument instrument = pair;
    if (instrument == null) {
      instrument = BinanceAdapters.adaptSymbol(symbol, isFuture );
    }
    if (ticker == null) {
      ticker =
          new Ticker.Builder()
              .instrument(instrument)
              .open(openPrice)
              .ask(askPrice)
              .bid(bidPrice)
              .last(lastPrice)
              .high(highPrice)
              .low(lowPrice)
              .volume(volume)
              .vwap(weightedAvgPrice)
              .askSize(askQty)
              .bidSize(bidQty)
              .quoteVolume(quoteVolume)
              .timestamp(closeTime > 0 ? new Date(closeTime) : null)
              .percentageChange(priceChangePercent)
              .build();
    }
    return ticker;
  }
}
