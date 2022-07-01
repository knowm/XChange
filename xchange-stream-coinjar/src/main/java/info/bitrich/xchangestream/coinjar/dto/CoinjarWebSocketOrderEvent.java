package info.bitrich.xchangestream.coinjar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinjarWebSocketOrderEvent extends CoinjarEvent {

  public final String topic;

  public final String event;

  public final Integer ref;

  public final Payload payload;

  public CoinjarWebSocketOrderEvent(
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

    public final Order order;

    public Payload(@JsonProperty("fill") Order order) {
      this.order = order;
    }

    public static class Order {
      public final String type;
      public final String timestamp;
      public final String timeInForce;
      public final String status;
      public final String size;
      public final String side;
      public final String ref;
      public final String productId;
      public final String price;
      public final long oid;
      public final String filled;
      public final String clientNumber;

      public Order(
          @JsonProperty("type") String type,
          @JsonProperty("timestamp") String timestamp,
          @JsonProperty("time_in_force") String timeInForce,
          @JsonProperty("status") String status,
          @JsonProperty("size") String size,
          @JsonProperty("side") String side,
          @JsonProperty("ref") String ref,
          @JsonProperty("product_id") String productId,
          @JsonProperty("price") String price,
          @JsonProperty("oid") long oid,
          @JsonProperty("filled") String filled,
          @JsonProperty("clientNumber") String clientNumber) {
        this.type = type;
        this.timestamp = timestamp;
        this.timeInForce = timeInForce;
        this.status = status;
        this.size = size;
        this.side = side;
        this.ref = ref;
        this.productId = productId;
        this.price = price;
        this.oid = oid;
        this.filled = filled;
        this.clientNumber = clientNumber;
      }
    }
  }
}
