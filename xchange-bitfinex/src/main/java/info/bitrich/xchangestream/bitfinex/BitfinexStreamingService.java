package info.bitrich.xchangestream.bitfinex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketUnSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexStreamingService extends JsonNettyStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(BitfinexStreamingService.class);

    private static final String INFO = "info";
    private static final String ERROR = "error";
    private static final String CHANNEL_ID = "chanId";
    private static final String SUBSCRIBED = "subscribed";
    private static final String UNSUBSCRIBED = "unsubscribed";

    private static final int SUBSCRIPTION_FAILED = 10300;

    private final Map<String, String> subscribedChannels = new HashMap<>();

    public BitfinexStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }

    @Override
    public void messageHandler(String message) {
        LOG.debug("Received message: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        // Parse incoming message to JSON
        try {
            jsonNode = objectMapper.readTree(message);
        } catch (IOException e) {
            LOG.error("Error parsing incoming message to JSON: {}", message);
            return;
        }

        handleMessage(jsonNode);
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if (message.isArray()) {
            String type = message.get(1).asText();
            if (type.equals("hb")) {
                return;
            }
        }

        JsonNode event = message.get("event");
        if (event != null) {
            if (event.textValue().equals(INFO)) {
                JsonNode version = message.get("version");
                if (version != null) {
                    LOG.debug("Bitfinex websocket API version: {}.", version.intValue());
                }
            } else if (event.textValue().equals(SUBSCRIBED)) {
                String channel = message.get("channel").asText();
                String pair = message.get("pair").asText();
                String channelId = message.get(CHANNEL_ID).asText();
                try {
                    String subscriptionUniqueId = getSubscriptionUniqueId(channel, pair);
                    subscribedChannels.put(channelId, subscriptionUniqueId);
                    LOG.debug("Register channel {}: {}", subscriptionUniqueId, channelId);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
            } else if (event.textValue().equals(UNSUBSCRIBED)) {
                String channelId = message.get(CHANNEL_ID).asText();
                subscribedChannels.remove(channelId);
            } else if (event.textValue().equals(ERROR)) {
                if (message.get("code").asInt() == SUBSCRIPTION_FAILED) {
                    LOG.error("Error with message: " + message.get("symbol") + " " + message.get("msg"));
                    return;
                }
                super.handleError(message, new ExchangeException("Error code: " + message.get("code").asText()));
            }
        } else super.handleMessage(message);
    }

    @Override
    public String getSubscriptionUniqueId(String channelName, Object... args) {
        return channelName + "-" + args[0].toString();
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String chanId;
        if (message.has(CHANNEL_ID)) {
            chanId = message.get(CHANNEL_ID).asText();
        } else {
            chanId = message.get(0).asText();
        }

        if (chanId == null) throw new IOException("Can't find CHANNEL_ID value");
        return subscribedChannels.get(chanId);
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        BitfinexWebSocketSubscriptionMessage subscribeMessage = null;
        if (args.length == 1) {
            subscribeMessage =
                    new BitfinexWebSocketSubscriptionMessage(channelName, (String) args[0]);
        } else if (args.length == 3) {
            subscribeMessage =
                    new BitfinexWebSocketSubscriptionMessage(channelName, (String) args[0], (String) args[1],
                            (String) args[2]);
        }
        if (subscribeMessage == null) throw new IOException("SubscribeMessage: Insufficient arguments");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        String channelId = null;
        for (Map.Entry<String, String> entry : subscribedChannels.entrySet()) {
            if (entry.getValue().equals(channelName)) {
                channelId = entry.getKey();
                break;
            }
        }

        if (channelId == null) throw new IOException("Can't find channel unique name");

        BitfinexWebSocketUnSubscriptionMessage subscribeMessage =
                new BitfinexWebSocketUnSubscriptionMessage(channelId);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);
    }
}
