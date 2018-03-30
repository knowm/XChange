package info.bitrich.xchangestream.hitbtc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.hitbtc.dto.HitbtcWebSocketBaseParams;
import info.bitrich.xchangestream.hitbtc.dto.HitbtcWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * Created by Pavel Chertalev on 15.03.2018.
 */
public class HitbtcStreamingService extends JsonNettyStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(HitbtcStreamingService.class);

    private static final String JSON_METHOD = "method";
    private static final String JSON_SYMBOL = "symbol";
    private static final String JSON_PARAMS = "params";
    private static final String JSON_RESULT = "result";

    private static final String OP_SNAPSHOT = "snapshot";
    private static final String OP_UPDATE = "update";

    private final Map<Integer, HitbtcWebSocketSubscriptionMessage> requests = new HashMap<>();

    private static final String REQUEST_ID = "id";

    public HitbtcStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {

        if (message.has(JSON_METHOD)) {
            String method = message.get(JSON_METHOD).asText();
            if (message.has(JSON_PARAMS) && message.get(JSON_PARAMS).has(JSON_SYMBOL)) {
                String symbol = message.get(JSON_PARAMS).get(JSON_SYMBOL).asText();

                return Stream.of(OP_UPDATE, OP_SNAPSHOT)
                        .filter(method::startsWith)
                        .map(name -> method.substring(name.length()).toLowerCase() + "-" + symbol)
                        .findFirst()
                        .orElse(method.toLowerCase() + "-" + symbol);
            }
            return method;
        }

        throw new IOException("Channel name can't be evaluated from message");
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if (message.has(REQUEST_ID)) {
            int requestId = message.get(REQUEST_ID).asInt();
            if (requests.containsKey(requestId)) {
                boolean result = message.get(JSON_RESULT).asBoolean();
                HitbtcWebSocketSubscriptionMessage subscriptionMessage = requests.get(requestId);
                if (!result) {
                    LOG.error("Executing HitBTC method '{}' has been failed", subscriptionMessage.getMethod());
                } else {
                    LOG.info("Executing HitBTC method '{}' successfully completed", subscriptionMessage.getMethod());
                }
                requests.remove(requestId);
                return;
            } else {
                LOG.error("Not requested result received with ID {}", requestId);
            }
        }

        String channel = getChannel(message);
        if (!channels.containsKey(channel)) {
            LOG.warn("The message has been received from disconnected channel '{}'. Skipped.", channel);
            return;
        }

        super.handleMessage(message);
    }


    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        HitbtcWebSocketSubscriptionMessage subscribeMessage = generateSubscribeMessage(channelName, "subscribe");
        requests.put(subscribeMessage.getId(), subscribeMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {

        HitbtcWebSocketSubscriptionMessage subscribeMessage = generateSubscribeMessage(channelName, "unsubscribe");
        requests.put(subscribeMessage.getId(), subscribeMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);

    }

    private HitbtcWebSocketSubscriptionMessage generateSubscribeMessage(String channelName, String methodType) throws IOException {

        String[] chanelInfo = channelName.split("-");
        if (chanelInfo.length < 2) {
            throw new IOException(methodType + " message: channel name must has format <channelName>-<Symbol> (e.g orderbook-ETHBTC)");
        }

        String method = methodType + StringUtils.capitalize(chanelInfo[0]);
        String symbol = chanelInfo[1];
        int requestId = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

        return new HitbtcWebSocketSubscriptionMessage(requestId, method, new HitbtcWebSocketBaseParams(symbol));
    }
}
