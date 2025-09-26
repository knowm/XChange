package info.bitrich.xchangestream.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString // Removed callSuper = true
public class CoinsphWebSocketBookTicker { // Removed "extends CoinsphWebSocketEvent"

  @JsonProperty("e")
  private final String eventType; // Added

  @JsonProperty("E")
  private final long eventTime; // Added

  @JsonProperty("u")
  private final long updateId; // Order book updateId

  @JsonProperty("s")
  private final String symbol;

  @JsonProperty("b")
  private final BigDecimal bidPrice;

  @JsonProperty("B")
  private final BigDecimal bidQty;

  @JsonProperty("a")
  private final BigDecimal askPrice;

  @JsonProperty("A")
  private final BigDecimal askQty;

  public CoinsphWebSocketBookTicker(
      @JsonProperty("e") String eventType, // Added
      @JsonProperty("E") long eventTime, // Added
      @JsonProperty("u") long updateId,
      @JsonProperty("s") String symbol,
      @JsonProperty("b") BigDecimal bidPrice,
      @JsonProperty("B") BigDecimal bidQty,
      @JsonProperty("a") BigDecimal askPrice,
      @JsonProperty("A") BigDecimal askQty) {
    this.eventType = eventType; // Added
    this.eventTime = eventTime; // Added
    this.updateId = updateId;
    this.symbol = symbol;
    this.bidPrice = bidPrice;
    this.bidQty = bidQty;
    this.askPrice = askPrice;
    this.askQty = askQty;
  }
}
