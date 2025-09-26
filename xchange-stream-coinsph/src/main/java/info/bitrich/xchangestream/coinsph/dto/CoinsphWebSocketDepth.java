package info.bitrich.xchangestream.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphWebSocketDepth {
  private final String eventType; // e
  private final long eventTime; // E
  private final String symbol; // s
  private final long firstUpdateId; // U
  private final long finalUpdateId; // u
  private final List<List<BigDecimal>> bids; // b
  private final List<List<BigDecimal>> asks; // a

  public CoinsphWebSocketDepth(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") long eventTime,
      @JsonProperty("s") String symbol,
      @JsonProperty("U") long firstUpdateId,
      @JsonProperty("u") long finalUpdateId,
      @JsonProperty("b") List<List<BigDecimal>> bids,
      @JsonProperty("a") List<List<BigDecimal>> asks) {
    this.eventType = eventType;
    this.eventTime = eventTime;
    this.symbol = symbol;
    this.firstUpdateId = firstUpdateId;
    this.finalUpdateId = finalUpdateId;
    this.bids = bids;
    this.asks = asks;
  }
}
