package info.bitrich.xchangestream.coinjar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinjarWebSocketUserTradeEvent extends CoinjarEvent {

  public final String topic;

  public final String event;

  public final Integer ref;

  public final Payload payload;

  public CoinjarWebSocketUserTradeEvent(
      @JsonProperty("topic") String topic,
      @JsonProperty("event") String event,
      @JsonProperty("ref") Integer ref,
      @JsonProperty("payload") Payload payload) {
    this.topic = topic;
    this.event = event;
    this.ref = ref;
    this.payload = payload;
  }

  public static class Payload {

    public final Fill fill;

    public Payload(@JsonProperty("fill") Fill fill) {
      this.fill = fill;
    }

    public static class Fill {
      public final String value;
      public final String timestamp;
      public final long tid;
      public final String size;
      public final String side;
      public final String productId;
      public final String price;
      public final long oid;
      public final String liquidity;

      public Fill(
          @JsonProperty("value") String value,
          @JsonProperty("timestamp") String timestamp,
          @JsonProperty("tid") long tid,
          @JsonProperty("size") String size,
          @JsonProperty("side") String side,
          @JsonProperty("product_id") String productId,
          @JsonProperty("price") String price,
          @JsonProperty("oid") long oid,
          @JsonProperty("liquidity") String liquidity) {
        this.value = value;
        this.timestamp = timestamp;
        this.tid = tid;
        this.size = size;
        this.side = side;
        this.productId = productId;
        this.price = price;
        this.oid = oid;
        this.liquidity = liquidity;
      }
    }
  }
}
