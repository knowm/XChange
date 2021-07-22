package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.google.common.collect.Sets;
import info.bitrich.xchangestream.binance.dto.BinanceWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceStreamingService extends JsonNettyStreamingService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BinanceStreamingService.class);

  private static final String RESULT = "result";
  private static final String IDENTIFIER = "id";

  private final ProductSubscription productSubscription;

  private boolean isLiveSubscriptionEnabled = false;
  private Map<Integer, BinanceWebSocketSubscriptionMessage> liveSubscriptionMessage =
      new ConcurrentHashMap<>();

  public BinanceStreamingService(String baseUri, ProductSubscription productSubscription) {
    super(baseUri, Integer.MAX_VALUE);
    this.productSubscription = productSubscription;
  }

  public BinanceStreamingService(
      String baseUri,
      ProductSubscription productSubscription,
      int maxFramePayloadLength,
      Duration connectionTimeout,
      Duration retryDuration,
      int idleTimeoutSeconds) {
    super(baseUri, maxFramePayloadLength, connectionTimeout, retryDuration, idleTimeoutSeconds);
    this.productSubscription = productSubscription;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return message.get("stream").asText();
  }

  @Override
  protected void handleMessage(JsonNode message) {

    final JsonNode result = message.get(RESULT);
    final JsonNode identifier = message.get(IDENTIFIER);

    // If there is a result field (with null as value) and there is an id field with value != null,
    // it's the response
    // from Live Subscribing/Unsubscribing stream
    // See
    // https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md#live-subscribingunsubscribing-to-streams
    if (result instanceof NullNode && identifier != null) {
      try {
        final Integer id = Integer.parseInt(identifier.asText());
        final BinanceWebSocketSubscriptionMessage subscriptionMessage =
            this.liveSubscriptionMessage.get(id);
        if (subscriptionMessage != null) {
          final String streamName = subscriptionMessage.getParams().get(0);
          switch (subscriptionMessage.getMethod()) {
            case SUBSCRIBE:
              LOGGER.info("Stream {} has been successfully subscribed", streamName);
              break;
            case UNSUBSCRIBE:
              LOGGER.info("Stream {} has been successfully unsubscribed", streamName);
              break;
          }
          this.liveSubscriptionMessage.remove(id);
        }
      } catch (final NumberFormatException exception) {
        // Nothing to do
      }
    } else {
      super.handleMessage(message);
    }
  }

  /**
   * We override this method because we must not use Live Subscription in case of reconnection. The
   * reason is that Binance has a Websocket limits to 5 incoming messages per second. If we pass
   * this limit the socket is closed automatically by Binance. See
   * https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md#websocket-limits
   * for more details. All the channels will be resubscribed at connection time.
   */
  @Override
  public void resubscribeChannels() {
    // Nothing to do, Subscriptions are made upon connection - no messages to sent
  }

  /** Get the live subscription message */
  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {

    if (isLiveSubscriptionEnabled) {
      updateConnectionUri(channelName, BinanceWebSocketSubscriptionMessage.MethodType.SUBSCRIBE);
      return generateMessage(BinanceWebSocketSubscriptionMessage.MethodType.SUBSCRIBE, channelName);
    }
    // No subscribe message required if Live Subscription is disabled
    return null;
  }

  /** Get the live unsubscription message */
  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {

    if (isLiveSubscriptionEnabled) {
      updateConnectionUri(channelName, BinanceWebSocketSubscriptionMessage.MethodType.UNSUBSCRIBE);
      return generateMessage(
          BinanceWebSocketSubscriptionMessage.MethodType.UNSUBSCRIBE, channelName);
    }
    // No unsubscribe message required if Live Unsubscription is disabled
    return null;
  }

  /**
   * This method is used to update the connection uri after we live subscribe to a stream or
   * unsubscribe from a stream. It is used if we need to reconnect to existing channels (we want to
   * connect to all streams once at connection time because Binance has a Websocket Limits of 5
   * incoming messages per second).
   */
  private void updateConnectionUri(
      String channelName, final BinanceWebSocketSubscriptionMessage.MethodType methodType) {

    final String baseConnectionUrl = uri.toString().substring(0, uri.toString().indexOf("=") + 1);
    final String subscribedChannels = uri.toString().substring(uri.toString().indexOf("=") + 1);
    final Set<String> channels =
        subscribedChannels.isEmpty()
            ? new HashSet<>()
            : Sets.newHashSet(subscribedChannels.split("/"));
    switch (methodType) {
      case SUBSCRIBE:
        channels.add(channelName);
        break;
      case UNSUBSCRIBE:
        channels.remove(channelName);
        break;
    }
    final String newConnectionUrl = baseConnectionUrl + String.join("/", channels);
    try {
      uri = new URI(newConnectionUrl);
    } catch (URISyntaxException exception) {
      throw new IllegalArgumentException("Error parsing URI " + newConnectionUrl, exception);
    }
  }

  private String generateMessage(
      final BinanceWebSocketSubscriptionMessage.MethodType methodType, final String channelName)
      throws IOException {

    final int identifier = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    final BinanceWebSocketSubscriptionMessage message =
        new BinanceWebSocketSubscriptionMessage(methodType, channelName, identifier);
    this.liveSubscriptionMessage.put(identifier, message);
    return objectMapper.writeValueAsString(message);
  }

  @Override
  public void sendMessage(String message) {

    if (isLiveSubscriptionEnabled) {
      super.sendMessage(message);
    }
    // If Live Subscription is disabled, Subscriptions are made upon connection - no messages are
    // sent.
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler.INSTANCE;
  }

  /**
   * The available subscriptions for this streaming service.
   *
   * @return The subscriptions for the currently open connection.
   */
  public ProductSubscription getProductSubscription() {
    return productSubscription;
  }

  public void enableLiveSubscription() {
    isLiveSubscriptionEnabled = true;
  }

  public void disableLiveSubscription() {
    isLiveSubscriptionEnabled = false;
  }

  public boolean isLiveSubscriptionEnabled() {
    return isLiveSubscriptionEnabled;
  }

  /**
   * Live Unsubscription from stream. This send a message through the websocket to Binance with
   * method UNSUBSCRIBE. (see
   * https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md#unsubscribe-to-a-stream
   * for more details) This is the only way to really stop receiving data from the stream
   * (Disposable.dispose() dispose the resource but don't stop the data to be received from
   * Binance).
   *
   * @param channelId e.g. btcusdt@depth, btcusdt@trade
   */
  public void unsubscribeChannel(final String channelId) {

    if (channels.remove(channelId) != null) {
      try {
        sendMessage(getUnsubscribeMessage(channelId));
      } catch (IOException e) {
        LOGGER.debug("Failed to unsubscribe channel: {} {}", channelId, e.toString());
      } catch (Exception e) {
        LOGGER.warn("Failed to unsubscribe channel: {}", channelId, e);
      }
    }
  }
}
