package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.kraken.dto.KrakenFuturesSubscriptionMessage;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextHandler;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.kraken.dto.account.KrakenWebsocketToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType.subscribe;
import static info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType.unsubscribe;

/**
 * @author makarid, pchertalev
 */
public class KrakenFuturesStreamingService extends JsonNettyStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(KrakenFuturesStreamingService.class);
    private static final String EVENT = "event";
    private final Map<String, String> channels = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    private final boolean isPrivate;
    private final Supplier<KrakenWebsocketToken> authData;
    private final Map<Integer, String> subscriptionRequestMap = new ConcurrentHashMap<>();

    public KrakenFuturesStreamingService(
            boolean isPrivate, String uri, final Supplier<KrakenWebsocketToken> authData) {
        super(uri, Integer.MAX_VALUE);
        this.isPrivate = isPrivate;
        this.authData = authData;
    }

//    public KrakenFuturesStreamingService(
//            boolean isPrivate,
//            String uri,
//            int maxFramePayloadLength,
//            Duration connectionTimeout,
//            Duration retryDuration,
//            int idleTimeoutSeconds,
//            final Supplier<KrakenWebsocketToken> authData) {
//        super(uri, maxFramePayloadLength, connectionTimeout, retryDuration, idleTimeoutSeconds);
//        this.isPrivate = isPrivate;
//        this.authData = authData;
//    }

    @Override
    public boolean processArrayMessageSeparately() {
        return false;
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
    }

    @Override
    protected void handleMessage(JsonNode message) {
        String channelName;
        KrakenFuturesSubscriptionMessage subscriptionMessage;
        try {
            JsonNode event = message.get(EVENT);
            KrakenEventType krakenEvent;
            if (event != null && (krakenEvent = KrakenEventType.getEvent(event.textValue())) != null) {
                switch (krakenEvent) {
                    case info:
                        LOG.info("Info received: {}", message);
                        break;
                    case pingStatus:
                        LOG.info("PingStatus received: {}", message);
                        break;
                    case pong:
                        LOG.debug("Pong received");
                        break;
                    case heartbeat:
                        LOG.debug("Heartbeat received");
                        break;
                    case subscribed:
                        channelName = getChannel(message);
                        subscriptionMessage = mapper.treeToValue(message, KrakenFuturesSubscriptionMessage.class);
                        channels.put(subscriptionMessage.getProduct_ids().get(0), channelName);
                        break;
                    case unsubscribed:
                        channelName = getChannel(message);
                        LOG.info("Channel {} has been unsubscribed", channelName);
                        subscriptionMessage = mapper.treeToValue(message, KrakenFuturesSubscriptionMessage.class);
                        channels.remove(subscriptionMessage.getProduct_ids().get(0), channelName);
                        break;
                    case error:
                    case alert:
                        LOG.error(
                                "Error received: {}",
                                message.has("errorMessage")
                                        ? message.get("errorMessage").asText()
                                        : message.toString());
                        break;
                    default:
                        LOG.warn("Unexpected event type has been received: {}", krakenEvent);
                }
                return;
            }
        } catch (JsonProcessingException e) {
            LOG.error("Error reading message: {}", e.getMessage(), e);
        }
        channelName = getChannel(message);
//        !message.isArray() ||
        if (channelName == null) {
            LOG.error("Unknown message: {}", message.toString());
            return;
        }

        super.handleMessage(message);
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String channelName = null;
        if (message.get("feed").asText().contains("book")) {
            if (message.has("product_id")) {
                channelName = "book" + KrakenStreamingMarketDataService.KRAKEN_CHANNEL_DELIMITER + message.get("product_id").asText();
            }
            if (message.has("product_ids")) {
                channelName = "book" + KrakenStreamingMarketDataService.KRAKEN_CHANNEL_DELIMITER + mapper.treeToValue(message.get("product_ids"), String[].class)[0].trim();
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ChannelName {}", StringUtils.isBlank(channelName) ? "not defined" : channelName);
        }
        return channelName;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        String[] channelData =
                channelName.split(KrakenStreamingMarketDataService.KRAKEN_CHANNEL_DELIMITER);

        String pair = channelData[1];

        KrakenFuturesSubscriptionMessage subscriptionMessage =
                new KrakenFuturesSubscriptionMessage(
                        subscribe,
                        KrakenSubscriptionName.book,
                        Collections.singletonList(pair));
        return objectMapper.writeValueAsString(subscriptionMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        String[] channelData =
                channelName.split(KrakenStreamingMarketDataService.KRAKEN_CHANNEL_DELIMITER);

        String pair = channelData[1];
        KrakenFuturesSubscriptionMessage subscriptionMessage =
                new KrakenFuturesSubscriptionMessage(
                        unsubscribe,
                        KrakenSubscriptionName.book,
                        Collections.singletonList(pair));
        return objectMapper.writeValueAsString(subscriptionMessage);
    }

    @Override
    protected WebSocketClientHandler getWebSocketClientHandler(
            WebSocketClientHandshaker handshaker,
            WebSocketClientHandler.WebSocketMessageHandler handler) {
        LOG.info("Registering KrakenWebSocketClientHandler");
        return new KrakenWebSocketClientHandler(handshaker, handler);
    }

    private final WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler = null;

    /**
     * Custom client handler in order to execute an external, user-provided handler on channel events.
     * This is useful because it seems Kraken unexpectedly closes the web socket connection.
     */
    class KrakenWebSocketClientHandler extends NettyWebSocketClientHandler {

        public KrakenWebSocketClientHandler(
                WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
            super(handshaker, handler);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            super.channelInactive(ctx);
            if (channelInactiveHandler != null) {
                channelInactiveHandler.onMessage("WebSocket Client disconnected!");
            }
        }
    }
}
