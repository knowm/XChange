package info.bitrich.xchangestream.poloniex2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.dto.Order;

/** Created by Lukas Zaoralek on 11.11.17. */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class PoloniexWebSocketEventsTransaction {
  private Long channelId;
  private Long seqId;
  private List<PoloniexWebSocketEvent> events;

  @JsonCreator
  public PoloniexWebSocketEventsTransaction(
      @JsonProperty("channelId") final Long channelId,
      @JsonProperty("seqId") final Long seqId,
      @JsonProperty("jsonEvents") final List<JsonNode> jsonEvents) {
    this.channelId = channelId;
    this.seqId = seqId;
    this.events = createEvents(jsonEvents);
  }

  public Long getChannelId() {
    return channelId;
  }

  public Long getSeqId() {
    return seqId;
  }

  public List<PoloniexWebSocketEvent> getEvents() {
    return events;
  }

  private List<PoloniexWebSocketEvent> createEvents(final List<JsonNode> jsonEvents) {
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    List<PoloniexWebSocketEvent> events = new ArrayList<>(jsonEvents.size());
    for (JsonNode jsonNode : jsonEvents) {
      String eventType = jsonNode.get(0).asText();
      switch (eventType) {
        case "i":
          {
            OrderbookInsertEvent orderbookInsertEvent =
                mapper.convertValue(jsonNode.get(1), OrderbookInsertEvent.class);
            PoloniexWebSocketOrderbookInsertEvent insertEvent =
                new PoloniexWebSocketOrderbookInsertEvent(orderbookInsertEvent);
            events.add(insertEvent);
            break;
          }
        case "o":
          {
            OrderbookModifiedEvent orderbookModifiedEvent =
                new OrderbookModifiedEvent(
                    jsonNode.get(1).asText(),
                    new BigDecimal(jsonNode.get(2).asText()),
                    new BigDecimal(jsonNode.get(3).asText()));
            PoloniexWebSocketOrderbookModifiedEvent insertEvent =
                new PoloniexWebSocketOrderbookModifiedEvent(orderbookModifiedEvent);
            events.add(insertEvent);
            break;
          }
        case "t":
          {
            /*
             * ["t", "<trade id>", <1 for buy 0 for sell>, "<price>", "<size>", <timestamp>]
             */
            String tradeId = jsonNode.get(1).asText();
            Order.OrderType side = sideFromString(jsonNode.get(2).asText());
            BigDecimal price = new BigDecimal(jsonNode.get(3).asText());
            BigDecimal size = new BigDecimal(jsonNode.get(4).asText());
            int timestampSeconds = jsonNode.get(5).asInt();
            TradeEvent tradeEvent = new TradeEvent(tradeId, side, price, size, timestampSeconds);
            PoloniexWebSocketTradeEvent insertEvent = new PoloniexWebSocketTradeEvent(tradeEvent);
            events.add(insertEvent);
            break;
          }
        default: // Ignore
      }
    }

    return events;
  }

  private static Order.OrderType sideFromString(String side) {
    if (side.equals("1")) {
      return Order.OrderType.BID;
    } else if (side.equals("0")) {
      return Order.OrderType.ASK;
    }
    throw new IllegalArgumentException("Unknown side: " + side);
  }
}
