package info.bitrich.xchangestream.gemini.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTrade;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiWebSocketTransaction {
  private String type;
  private String eventId;
  private String socket_sequence;
  private String timestamp;
  private String timestampms;
  private JsonNode events;

  public GeminiWebSocketTransaction(
      @JsonProperty("type") String type,
      @JsonProperty("eventId") String eventId,
      @JsonProperty("socket_sequence") String socket_sequence,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("timestampms") String timestampms,
      @JsonProperty("events") JsonNode events) {
    this.type = type;
    this.eventId = eventId;
    this.socket_sequence = socket_sequence;
    this.timestamp = timestamp;
    this.timestampms = timestampms;
    this.events = events;
  }

  public String getType() {
    return type;
  }

  public String getEventId() {
    return eventId;
  }

  public String getSocket_sequence() {
    return socket_sequence;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getTimestampms() {
    return timestampms;
  }

  public JsonNode getEvents() {
    return events;
  }

  private static GeminiLimitOrder toGeminiLimitOrder(JsonNode jsonEvent) {
    BigDecimal price = new BigDecimal(jsonEvent.get("price").asText());
    BigDecimal amount = new BigDecimal(jsonEvent.get("remaining").asText());
    BigDecimal timestamp = new BigDecimal((new Date().getTime() / 1000));
    Order.OrderType side =
        jsonEvent.get("side").asText().equals("ask") ? Order.OrderType.ASK : Order.OrderType.BID;
    return new GeminiLimitOrder(side, price, amount, timestamp);
  }

  public GeminiLimitOrder[] toGeminiLimitOrdersUpdate() {
    List<GeminiLimitOrder> levels = new ArrayList<>(1000);
    for (JsonNode jsonEvent : events) {
      if (!jsonEvent.has("reason")) continue;
      if (jsonEvent.get("reason").asText().equals("initial")
          || jsonEvent.get("reason").asText().equals("place")
          || jsonEvent.get("reason").asText().equals("cancel")
          || jsonEvent.get("reason").asText().equals("trade")) {
        GeminiLimitOrder level = toGeminiLimitOrder(jsonEvent);
        levels.add(level);
      }
    }

    return levels.toArray(new GeminiLimitOrder[levels.size()]);
  }

  public GeminiOrderbook toGeminiOrderbook(CurrencyPair currencyPair) {
    GeminiLimitOrder[] levels = toGeminiLimitOrdersUpdate();
    GeminiOrderbook orderbook = new GeminiOrderbook(currencyPair);
    orderbook.createFromLevels(levels);
    return orderbook;
  }

  private static GeminiTrade toGeminiTrade(JsonNode jsonEvent, long timestamp) {
    long tid = Long.valueOf(jsonEvent.get("tid").asText());
    BigDecimal price = new BigDecimal(jsonEvent.get("price").asText());
    BigDecimal amount = new BigDecimal(jsonEvent.get("amount").asText());
    String takerSide = jsonEvent.get("makerSide").asText().equals("ask") ? "buy" : "sell";
    return new GeminiTrade(price, amount, timestamp, "gemini", tid, takerSide);
  }

  public GeminiTrade[] toGeminiTrades() {
    long timestamp = Long.valueOf(this.timestamp);
    List<GeminiTrade> trades = new ArrayList<>(1000);
    for (JsonNode jsonEvent : events) {
      if (jsonEvent.get("type").asText().equals("trade")) {
        GeminiTrade trade = toGeminiTrade(jsonEvent, timestamp);
        trades.add(trade);
      }
    }

    return trades.toArray(new GeminiTrade[trades.size()]);
  }
}
