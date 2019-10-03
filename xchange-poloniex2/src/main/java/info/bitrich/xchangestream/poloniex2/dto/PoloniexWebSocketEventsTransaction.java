package info.bitrich.xchangestream.poloniex2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Lukas Zaoralek on 11.11.17.
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class PoloniexWebSocketEventsTransaction {
    private Long channelId;
    private Long seqId;
    private List<PoloniexWebSocketEvent> events;

    @JsonCreator
    public PoloniexWebSocketEventsTransaction(
            @JsonProperty("channelId") final Long channelId,
            @JsonProperty("seqId") final Long seqId,
            @JsonProperty("jsonEvents") final List<JsonNode> jsonEvents
    ) {
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
            if (eventType.equals("i")) {
                OrderbookInsertEvent orderbookInsertEvent = mapper.convertValue(jsonNode.get(1), OrderbookInsertEvent.class);
                PoloniexWebSocketOrderbookInsertEvent insertEvent = new PoloniexWebSocketOrderbookInsertEvent(orderbookInsertEvent);
                events.add(insertEvent);
            } else if (eventType.equals("o")) {
                OrderbookModifiedEvent orderbookModifiedEvent = new OrderbookModifiedEvent(jsonNode.get(1).asText(),
                        new BigDecimal(jsonNode.get(2).asText()), new BigDecimal(jsonNode.get(3).asText()));
                PoloniexWebSocketOrderbookModifiedEvent insertEvent = new PoloniexWebSocketOrderbookModifiedEvent(orderbookModifiedEvent);
                events.add(insertEvent);
            } else if (eventType.equals("t")) {
                String formatedDate = "";
                Instant timestamp = Instant.ofEpochSecond(jsonNode.get(5).asInt());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                formatedDate = sdf.format(Date.from(timestamp));
                TradeEvent tradeEvent = new TradeEvent(jsonNode.get(1).asText(),
                        jsonNode.get(2).asText(),
                        new BigDecimal(jsonNode.get(3).asText()),
                        new BigDecimal(jsonNode.get(4).asText()),
                        formatedDate);
                PoloniexWebSocketTradeEvent insertEvent = new PoloniexWebSocketTradeEvent(tradeEvent);
                events.add(insertEvent);
            }
        }

        return events;
    }
}
