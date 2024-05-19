package info.bitrich.xchangestream.kraken;

import static info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType.subscribe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.kraken.dto.KrakenEvent;
import info.bitrich.xchangestream.kraken.dto.KrakenSubscriptionConfig;
import info.bitrich.xchangestream.kraken.dto.KrakenSubscriptionMessage;
import info.bitrich.xchangestream.kraken.dto.KrakenSubscriptionStatusMessage;
import info.bitrich.xchangestream.kraken.dto.KrakenSystemStatus;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextHandler;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kraken.dto.account.KrakenWebsocketToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author makarid, pchertalev
 */
public class KrakenStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenStreamingService.class);
  private static final String EVENT = "event";
  private static final String WEBSOCKET_REQUESTS_PER_SECOND =
      "Kraken_Websocket_Requests_Per_Second";
  private final Map<Integer, String> channels = new ConcurrentHashMap<>();
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final boolean isPrivate;
  private final Supplier<KrakenWebsocketToken> authData;
  private final Map<Integer, String> subscriptionRequestMap = new ConcurrentHashMap<>();
  private final Map<String, ObservableEmitter<KrakenEvent>> systemChannels =
      new ConcurrentHashMap<>();
  private final RateLimiter rateLimiter;
  static final int ORDER_BOOK_SIZE_DEFAULT = 10;
  private static final int[] KRAKEN_VALID_ORDER_BOOK_SIZES = {10, 25, 100, 500, 1000};

  public KrakenStreamingService(
      KrakenStreamingExchange exchange,
      boolean isPrivate,
      String uri,
      final Supplier<KrakenWebsocketToken> authData) {
    super(uri, Integer.MAX_VALUE);
    this.isPrivate = isPrivate;
    this.authData = authData;
    rateLimiter = initRateLimiter(exchange);
  }

  public KrakenStreamingService(
      KrakenStreamingExchange exchange,
      boolean isPrivate,
      String uri,
      int maxFramePayloadLength,
      Duration connectionTimeout,
      Duration retryDuration,
      int idleTimeoutSeconds,
      final Supplier<KrakenWebsocketToken> authData) {
    super(uri, maxFramePayloadLength, connectionTimeout, retryDuration, idleTimeoutSeconds);
    this.isPrivate = isPrivate;
    this.authData = authData;
    rateLimiter = initRateLimiter(exchange);
  }

  private static RateLimiter initRateLimiter(KrakenStreamingExchange exchange) {
    RateLimiter rateLimiter = null;
    Integer requestsPerSecond =
        (Integer)
            exchange
                .getExchangeSpecification()
                .getExchangeSpecificParametersItem(WEBSOCKET_REQUESTS_PER_SECOND);
    if (requestsPerSecond != null) {
      // N messages per second
      rateLimiter =
          RateLimiter.of(
              "websocket rate limiter",
              RateLimiterConfig.custom()
                  .limitForPeriod(requestsPerSecond)
                  .limitRefreshPeriod(Duration.ofSeconds(1))
                  .build());

    }
    return rateLimiter;
  }

  @Override
  public boolean processArrayMessageSeparately() {
    return false;
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
  }

  public Observable<KrakenEvent> subscribeSystemChannel(KrakenEventType eventType) {
    String channelName = eventType.name();
    return Observable.<KrakenEvent>create(
            e -> systemChannels.computeIfAbsent(channelName, cid -> e))
        .doOnDispose(() -> systemChannels.remove(channelName))
        .share();
  }

  @Override
  public Completable disconnect() {
    systemChannels.clear();
    return super.disconnect();
  }

  @Override
  protected void handleMessage(JsonNode message) {

    String channelName = getChannel(message);

    try {
      JsonNode event = message.get(EVENT);
      KrakenEventType krakenEvent;
      if (event != null && (krakenEvent = KrakenEventType.getEvent(event.textValue())) != null) {
        switch (krakenEvent) {
          case pingStatus:
            LOG.info("PingStatus received: {}", message);
            break;
          case pong:
            LOG.debug("Pong received");
            break;
          case heartbeat:
            LOG.debug("Heartbeat received");
            break;
          case systemStatus:
            KrakenSystemStatus systemStatus = mapper.treeToValue(message, KrakenSystemStatus.class);
            LOG.info("System status: {}", systemStatus);
            // send to subscribers if any
            ObservableEmitter<KrakenEvent> emitter = systemChannels.get(krakenEvent.name());

            if (emitter != null) {
              emitter.onNext(systemStatus);
            }
            break;
          case subscriptionStatus:
            LOG.debug("Received subscriptionStatus message {}", message);
            KrakenSubscriptionStatusMessage statusMessage =
                mapper.treeToValue(message, KrakenSubscriptionStatusMessage.class);
            Integer reqid = statusMessage.getReqid();
            if (!isPrivate && reqid != null) {
              channelName = subscriptionRequestMap.remove(reqid);
            }
            statusMessage.setChannelName(channelName);

            Integer channelId = statusMessage.getChannelID();
            switch (statusMessage.getStatus()) {
              case subscribed:
                LOG.info("Channel name={}, id={} has been subscribed", channelName, channelId);

                if (channelId != null) {
                  channels.put(channelId, channelName);
                }

                break;
              case unsubscribed:
                LOG.info("Channel name={}, id={} has been unsubscribed", channelName, channelId);
                channels.remove(channelId);
                break;
              case error:
                LOG.error(
                    "Channel name={}, id={} has been failed: {}", channelName, channelId,
                    statusMessage.getErrorMessage());
                if ("ESession:Invalid session".equals(statusMessage.getErrorMessage())) {
                  throw new ExchangeException("Issue with session validity");
                }
            }
            // send to subscribers if any
            emitter = systemChannels.get(krakenEvent.name());
            if (emitter != null) {
              emitter.onNext(statusMessage);
            }
            break;
          case error:
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

    if (!message.isArray() || channelName == null) {
      LOG.error("Unknown message:  isArray={}, name={}, message={}", message.isArray(), channelName,
          message);
      return;
    }

    super.handleMessage(message);
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    String channelName = null;
    if (message.has("channelID")) {
      int channelId = message.get("channelID").asInt();
      return channels.getOrDefault(channelId, String.valueOf(channelId));
    }
    if (message.has("channelName")) {
      channelName = message.get("channelName").asText();
      return channelName;
    }

    if (message.isArray()) {
      if (message.get(0).isInt()) {
        LOG.trace("Taking channelName from ID from first field INT).");
        int channelId = message.get(0).asInt();
        return channels.getOrDefault(channelId, String.valueOf(channelId));
      }
      if (message.get(1).isTextual()) {
        channelName = message.get(1).asText();
        return channelName;
      }
    }

    if (LOG.isTraceEnabled()) {
      LOG.trace("ChannelName {}", StringUtils.isBlank(channelName) ? "not defined" : channelName);
    }
    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    int reqID = Math.abs(UUID.randomUUID().hashCode());

    Integer interval = args != null && args.length > 1 ? (Integer) args[1] : null;

    String[] channelData =
        channelName.split(KrakenStreamingMarketDataService.KRAKEN_CHANNEL_DELIMITER);
    KrakenSubscriptionName subscriptionName = KrakenSubscriptionName.valueOf(channelData[0]);

    if (isPrivate) {
      final String token = authData.get().getToken();

      KrakenSubscriptionMessage subscriptionMessage =
          new KrakenSubscriptionMessage(
              reqID, subscribe, null,
              new KrakenSubscriptionConfig(subscriptionName, null, interval, token));

      String subscriptionMessageString = objectMapper.writeValueAsString(subscriptionMessage);
      return subscriptionMessageString;
    } else {
      String pair = channelData[1];

      subscriptionRequestMap.put(reqID, getSubscriptionUniqueId(channelName, args));

      KrakenSubscriptionMessage subscriptionMessage =
          new KrakenSubscriptionMessage(
              reqID,
              subscribe,
              Collections.singletonList(pair),
              new KrakenSubscriptionConfig(subscriptionName, parseOrderBookSize(args), interval,
                  null));
      String subscriptionMessageString = objectMapper.writeValueAsString(subscriptionMessage);
      return subscriptionMessageString;
    }
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    int reqID = Math.abs(UUID.randomUUID().hashCode());

    Integer interval = args != null && args.length > 1 ? (Integer) args[1] : null;

    String[] channelData =
        channelName.split(KrakenStreamingMarketDataService.KRAKEN_CHANNEL_DELIMITER);
    KrakenSubscriptionName subscriptionName = KrakenSubscriptionName.valueOf(channelData[0]);

    if (isPrivate) {
      KrakenSubscriptionMessage subscriptionMessage =
          new KrakenSubscriptionMessage(
              reqID,
              KrakenEventType.unsubscribe,
              null,
              new KrakenSubscriptionConfig(subscriptionName));
      return objectMapper.writeValueAsString(subscriptionMessage);
    } else {
      String pair = channelData[1];

      subscriptionRequestMap.put(reqID, channelName);
      KrakenSubscriptionMessage subscriptionMessage =
          new KrakenSubscriptionMessage(
              reqID,
              KrakenEventType.unsubscribe,
              Collections.singletonList(pair),
              new KrakenSubscriptionConfig(subscriptionName, parseOrderBookSize(args), interval,
                  null));
      return objectMapper.writeValueAsString(subscriptionMessage);
    }
  }

  @Override
  public void sendMessage(String message) {
    if (rateLimiter != null) {
      RateLimiter.waitForPermission(rateLimiter);
    }

    super.sendMessage(message);
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

  static Integer parseOrderBookSize(Object[] args) {
    if (args != null && args.length > 0) {
      Object obSizeParam = args[0];
      LOG.debug("Specified Kraken order book size: {}", obSizeParam);
      if (obSizeParam != null && Number.class.isAssignableFrom(obSizeParam.getClass())) {
        int obSize = ((Number) obSizeParam).intValue();
        if (ArrayUtils.contains(KRAKEN_VALID_ORDER_BOOK_SIZES, obSize)) {
          return obSize;
        }
        LOG.error(
            "Invalid order book size {}. Valid values: {}. Default order book size has been used: {}",
            obSize,
            ArrayUtils.toString(KRAKEN_VALID_ORDER_BOOK_SIZES),
            ORDER_BOOK_SIZE_DEFAULT);
        return ORDER_BOOK_SIZE_DEFAULT;
      }
    }
    return null;
  }
}