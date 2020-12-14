package info.bitrich.xchangestream.coinjar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinjarWebSocketBookEvent extends CoinjarEvent {

  public static final String INIT = "init";
  public static final String UPDATE = "update";

  public final String topic;

  public final String event;

  public final Integer ref;

  public final Payload payload;

  public CoinjarWebSocketBookEvent(
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
    public final List<Order> bids;
    public final List<Order> asks;

    public Payload(
        @JsonProperty("bids") List<List<String>> bids,
        @JsonProperty("asks") List<List<String>> asks) {
      if (bids == null) {
        this.bids = new ArrayList<>();
      } else {
        this.bids =
            bids.stream().map(it -> new Order(it.get(0), it.get(1))).collect(Collectors.toList());
      }
      if (asks == null) {
        this.asks = new ArrayList<>();
      } else {
        this.asks =
            asks.stream().map(it -> new Order(it.get(0), it.get(1))).collect(Collectors.toList());
      }
    }

    public static class Order {
      public final String price;
      public final String volume;

      public Order(String price, String volume) {
        this.price = price;
        this.volume = volume;
      }
    }
  }
}
