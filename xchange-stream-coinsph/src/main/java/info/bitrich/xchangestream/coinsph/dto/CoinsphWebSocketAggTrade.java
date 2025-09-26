package info.bitrich.xchangestream.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphWebSocketAggTrade {
  private final String eventType; // e
  private final long eventTime; // E
  private final String symbol; // s
  private final long aggregateTradeId; // a
  private final BigDecimal price; // p
  private final BigDecimal quantity; // q
  private final long firstTradeId; // f
  private final long lastTradeId; // l
  private final long tradeTime; // T
  private final boolean buyerMaker; // m

  public CoinsphWebSocketAggTrade(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") long eventTime,
      @JsonProperty("s") String symbol,
      @JsonProperty("a") long aggregateTradeId,
      @JsonProperty("p") BigDecimal price,
      @JsonProperty("q") BigDecimal quantity,
      @JsonProperty("f") long firstTradeId,
      @JsonProperty("l") long lastTradeId,
      @JsonProperty("T") long tradeTime,
      @JsonProperty("m") boolean buyerMaker) {
    this.eventType = eventType;
    this.eventTime = eventTime;
    this.symbol = symbol;
    this.aggregateTradeId = aggregateTradeId;
    this.price = price;
    this.quantity = quantity;
    this.firstTradeId = firstTradeId;
    this.lastTradeId = lastTradeId;
    this.tradeTime = tradeTime;
    this.buyerMaker = buyerMaker;
  }
}
