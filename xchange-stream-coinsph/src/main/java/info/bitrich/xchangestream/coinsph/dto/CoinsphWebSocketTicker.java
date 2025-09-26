package info.bitrich.xchangestream.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString // Removed callSuper = true
public class CoinsphWebSocketTicker { // Removed "extends CoinsphWebSocketEvent"

  @JsonProperty("e")
  private final String eventType; // Added

  @JsonProperty("E")
  private final long eventTime; // Added

  @JsonProperty("s")
  private final String symbol;

  @JsonProperty("p")
  private final BigDecimal priceChange;

  @JsonProperty("P")
  private final BigDecimal priceChangePercent;

  @JsonProperty("w")
  private final BigDecimal weightedAvgPrice;

  @JsonProperty("x")
  private final BigDecimal prevClosePrice; // Or first trade price

  @JsonProperty("c")
  private final BigDecimal lastPrice;

  @JsonProperty("Q")
  private final BigDecimal lastQty;

  @JsonProperty("b")
  private final BigDecimal bidPrice;

  @JsonProperty("B")
  private final BigDecimal bidQty;

  @JsonProperty("a")
  private final BigDecimal askPrice;

  @JsonProperty("A")
  private final BigDecimal askQty;

  @JsonProperty("o")
  private final BigDecimal openPrice;

  @JsonProperty("h")
  private final BigDecimal highPrice;

  @JsonProperty("l")
  private final BigDecimal lowPrice;

  @JsonProperty("v")
  private final BigDecimal volume; // Total traded base asset volume

  @JsonProperty("q")
  private final BigDecimal quoteVolume; // Total traded quote asset volume

  @JsonProperty("O")
  private final long openTime;

  @JsonProperty("C")
  private final long closeTime;

  @JsonProperty("F")
  private final long firstId; // First trade ID

  @JsonProperty("L")
  private final long lastId; // Last trade ID

  @JsonProperty("n")
  private final long count; // Total number of trades

  public CoinsphWebSocketTicker(
      @JsonProperty("e") String eventType, // Added
      @JsonProperty("E") long eventTime, // Added
      @JsonProperty("s") String symbol,
      @JsonProperty("p") BigDecimal priceChange,
      @JsonProperty("P") BigDecimal priceChangePercent,
      @JsonProperty("w") BigDecimal weightedAvgPrice,
      @JsonProperty("x") BigDecimal prevClosePrice,
      @JsonProperty("c") BigDecimal lastPrice,
      @JsonProperty("Q") BigDecimal lastQty,
      @JsonProperty("b") BigDecimal bidPrice,
      @JsonProperty("B") BigDecimal bidQty,
      @JsonProperty("a") BigDecimal askPrice,
      @JsonProperty("A") BigDecimal askQty,
      @JsonProperty("o") BigDecimal openPrice,
      @JsonProperty("h") BigDecimal highPrice,
      @JsonProperty("l") BigDecimal lowPrice,
      @JsonProperty("v") BigDecimal volume,
      @JsonProperty("q") BigDecimal quoteVolume,
      @JsonProperty("O") long openTime,
      @JsonProperty("C") long closeTime,
      @JsonProperty("F") long firstId,
      @JsonProperty("L") long lastId,
      @JsonProperty("n") long count) {
    this.eventType = eventType; // Added
    this.eventTime = eventTime; // Added
    this.symbol = symbol;
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
  }
}
