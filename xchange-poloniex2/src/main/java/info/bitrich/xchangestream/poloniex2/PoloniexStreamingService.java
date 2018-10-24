package info.bitrich.xchangestream.poloniex2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.poloniex2.dto.PoloniexWebSocketEvent;
import info.bitrich.xchangestream.poloniex2.dto.PoloniexWebSocketEventsTransaction;
import info.bitrich.xchangestream.poloniex2.dto.PoloniexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukas Zaoralek on 10.11.17.
 */
public class PoloniexStreamingService extends JsonNettyStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(PoloniexStreamingService.class);

    private static final String HEARTBEAT = "1010";

    private final Map<String, String> subscribedChannels = new HashMap<>();
    private final Map<String, Observable<JsonNode>> subscriptions = new HashMap<>();


    public PoloniexStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if (message.isArray()) {
            Integer channelId = new Integer(message.get(0).toString());
            if (channelId > 0 && channelId < 1000) {
                JsonNode events = message.get(2);
                if (events.isArray()) {
                    JsonNode event = events.get(0);
                    if (event.get(0).toString().equals("\"i\"")) {
                        if (event.get(1).has("orderBook")) {
                            String currencyPair = event.get(1).get("currencyPair").asText();
                            LOG.info("Register {} as {}", String.valueOf(channelId), currencyPair);
                            subscribedChannels.put(String.valueOf(channelId), currencyPair);
                        }
                    }
                }
            }
        }
        if (message.has("error")) {
            LOG.error("Error with message: " + message.get("error").asText());
            return;
        }
        super.handleMessage(message);
    }

    @Override
    public void messageHandler(String message) {
        LOG.debug("Received message: {}", message);
        JsonNode jsonNode;

        // Parse incoming message to JSON
        try {
            jsonNode = objectMapper.readTree(message);
        } catch (IOException e) {
            LOG.error("Error parsing incoming message to JSON: {}", message);
            return;
        }

        if (jsonNode.isArray() && jsonNode.size() < 3) {
            if (jsonNode.get(0).asText().equals(HEARTBEAT)) return;
            else if (jsonNode.get(0).asText().equals("1002")) return;
        }

        handleMessage(jsonNode);
    }

    @Override
    public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
        if (!channels.containsKey(channelName)) {
            Observable<JsonNode> subscription = super.subscribeChannel(channelName, args);
            subscriptions.put(channelName, subscription);
        }

        return subscriptions.get(channelName);
    }

    public Observable<PoloniexWebSocketEvent> subscribeCurrencyPairChannel(CurrencyPair currencyPair) {
        String channelName = currencyPair.counter.toString() + "_" + currencyPair.base.toString();
        return subscribeChannel(channelName)
                .flatMapIterable(s -> {
                    PoloniexWebSocketEventsTransaction transaction = objectMapper.treeToValue(s, PoloniexWebSocketEventsTransaction.class);
                    return Arrays.asList(transaction.getEvents());
                }).share();
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String strChannelId = message.get(0).asText();
        Integer channelId = new Integer(strChannelId);
        if (channelId >= 1000) return strChannelId;
        else return subscribedChannels.get(message.get(0).asText());
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        PoloniexWebSocketSubscriptionMessage subscribeMessage = new PoloniexWebSocketSubscriptionMessage("subscribe",
                channelName);

        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        PoloniexWebSocketSubscriptionMessage subscribeMessage = new PoloniexWebSocketSubscriptionMessage("unsubscribe",
                channelName);

        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public Completable disconnect() {

        return super.disconnect();
    }

}
