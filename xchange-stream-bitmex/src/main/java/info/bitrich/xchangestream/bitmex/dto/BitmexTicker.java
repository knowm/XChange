package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.dto.marketdata.Ticker;

/** Created by Lukas Zaoralek on 13.11.17. */
public class BitmexTicker extends BitmexMarketDataEvent {
  private final String timestamp;
  private final String symbol;
  private final BigDecimal bidSize;
  private final BigDecimal bidPrice;
  private final BigDecimal askPrice;
  private final BigDecimal askSize;

  public BitmexTicker(
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("bidSize") BigDecimal bidSize,
      @JsonProperty("bidPrice") BigDecimal bidPrice,
      @JsonProperty("askPrice") BigDecimal askPrice,
      @JsonProperty("askSize") BigDecimal askSize) {
    super(symbol, timestamp);
    this.timestamp = timestamp;
    this.symbol = symbol;
    this.bidSize = bidSize;
    this.bidPrice = bidPrice;
    this.askPrice = askPrice;
    this.askSize = askSize;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getBidSize() {
    return bidSize;
  }

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }

  public BigDecimal getAskSize() {
    return askSize;
  }

  public Ticker toTicker() {
    return new Ticker.Builder()
        .ask(askPrice)
        .bidSize(bidSize)
        .bid(bidPrice)
        .askSize(askSize)
        .timestamp(getDate())
        .currencyPair(getCurrencyPair())
        .build();
  }
}
