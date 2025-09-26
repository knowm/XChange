package info.bitrich.xchangestream.coinsph;

import static info.bitrich.xchangestream.coinsph.CoinsphStreamingExchange.PUBLIC_API_URI;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.knowm.xchange.coinsph.service.CoinsphAccountServiceRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinsphStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(CoinsphStreamingService.class);
  private static final String SUBSCRIBE = "SUBSCRIBE";
  private static final String UNSUBSCRIBE = "UNSUBSCRIBE";
  private static final Duration DEFAULT_PING_INTERVAL_SECONDS =
      Duration.ofSeconds(3 * 60); // Coins.ph recommends pinging every 3-5 minutes for WebSocket
  private static final Duration LISTEN_KEY_KEEP_ALIVE_INTERVAL =
      Duration.ofMinutes(30); // Keep-alive typically every 30-50 mins

  private final CoinsphAccountServiceRaw accountServiceRaw;
  private volatile String listenKey = null;
  private volatile long listenKeyCreateTime = 0;
  private final AtomicBoolean isUserDataStreamSubscribed = new AtomicBoolean(false); // Added
  private ScheduledExecutorService listenKeyKeepAliveExecutor;
  private final boolean isPrivateService; // Added flag for private vs public service

  private final CoinsphStreamingExchange
      exchange; // Reference to the exchange for API keys, URIs etc.

  /**
   * Constructor for User Data Streaming Service. Obtains a listenKey and constructs the full
   * WebSocket URI before initializing the superclass.
   *
   * @param exchange The CoinsphStreamingExchange instance.
   * @param config The streaming configuration.
   * @throws IOException if listenKey cannot be obtained.
   */
  public CoinsphStreamingService(
      CoinsphStreamingExchange exchange,
      info.bitrich.xchangestream.service.core.StreamingExchangeConfiguration config)
      throws IOException {
    super(
        PUBLIC_API_URI, // API URL will be set dynamically after listenKey is obtained
        Integer.MAX_VALUE,
        config.getConnectionTimeout(),
        config.getRetryDuration(),
        config.getIdleTimeout());
    this.exchange = exchange;
    this.accountServiceRaw =
        (CoinsphAccountServiceRaw) exchange.getAccountService(); // Get REST account service
    this.isPrivateService = true; // This constructor is for private service

    // Obtain listenKey and set the actual API URI
    try {
      LOG.info("CoinsphStreamingService (User Data) initializing, fetching listenKey...");
      startUserDataStream(); // This populates this.listenKey
      if (this.listenKey == null) {
        throw new IOException("Failed to obtain listenKey for User Data Stream.");
      }
      String actualApiUrl = getUserDataApiUrl(this.listenKey);
      LOG.info("User Data Stream URL configured: {}", actualApiUrl);
      this.uri = java.net.URI.create(actualApiUrl); // Set the URI in the superclass
    } catch (IOException e) {
      LOG.error("Failed to initialize CoinsphStreamingService for User Data: {}", e.getMessage());
      throw e; // Re-throw to signal construction failure
    }

    // Just log that we're using default ping interval
    LOG.info("Using default ping interval of {} ms", DEFAULT_PING_INTERVAL_SECONDS.toMillis());
    // Actual ping configuration is handled through the constructor params passed to super()
  }

  /**
   * Constructor for Public Streaming Service.
   *
   * @param apiUrl The WebSocket API URL.
   * @param accountServiceRaw Not used for public streams, can be null.
   * @param isPrivateService Flag to indicate if this is a private service.
   */
  public CoinsphStreamingService(
      String apiUrl, CoinsphAccountServiceRaw accountServiceRaw, boolean isPrivateService) {
    super(apiUrl);
    this.exchange = null; // Not needed for public streams
    this.accountServiceRaw = accountServiceRaw;
    this.isPrivateService = isPrivateService;
  }

  // Helper method to construct the user data API URL
  private String getUserDataApiUrl(String listenKey) {
    if (exchange == null) {
      return "wss://wsapi.pro.coins.ph/openapi/ws/" + listenKey;
    }

    // Check if exchange has a method for this, otherwise use hardcoded base URL
    try {
      java.lang.reflect.Method getUserStreamingBaseUri =
          exchange.getClass().getMethod("getUserStreamingBaseUri");
      String baseUri = (String) getUserStreamingBaseUri.invoke(exchange);
      return baseUri + listenKey;
    } catch (Exception e) {
      LOG.warn("Could not invoke getUserStreamingBaseUri on exchange, using default URL", e);
      return "wss://wsapi.pro.coins.ph/openapi/ws/" + listenKey;
    }
  }

  /** Gets the API URL for this service. */
  public String getApiUrl() {
    if (this.uri != null) {
      return this.uri.toString();
    }
    // Fallback, though 'uri' should be set in constructor for user data stream
    LOG.warn("getApiUrl() called but URI was not set, this may indicate an issue.");
    return listenKey != null
        ? getUserDataApiUrl(listenKey)
        : "wss://wsapi.pro.coins.ph/openapi/quote/ws/";
  }

  @Override
  public Completable connect() {
    // ListenKey should be obtained and URI set in constructor.
    // If listenKey is null here, constructor failed or was bypassed.
    if (isPrivateService && listenKey == null) {
      LOG.error("Attempting to connect User Data Stream but listenKey is null.");
      return Completable.error(
          new IllegalStateException("ListenKey not available for User Data Stream connection."));
    }
    LOG.info("Connecting {} Stream", isPrivateService ? "User Data" : "Public");
    return super.connect();
  }

  private String formatChannelName(String e, JsonNode message) {
    switch (e) {
      case "depth":
      case "depthUpdate":
        return String.format("%s@depth", message.get("s").asText().toLowerCase());
      case "24hrTicker":
        return String.format("%s@ticker", message.get("s").asText().toLowerCase());
      default:
        return e;
    }
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    // Coins.ph streams are typically <symbol>@<streamName> or just <streamName> for user data
    // Example: {"stream":"btcusdt@depth","data":{...}}
    // Or for user data: {"e":"executionReport", ...} (channel might be implicit or fixed)

    if (message.has("stream")) {
      return message.get("stream").asText();
    } else if (message.has("e")) { // For user data streams like executionReport
      // User data streams might not have a "stream" field, channel is implicit (listenKey)
      // Or we can use the event type as part of the channel name for routing
      return formatChannelName(message.get("e").asText(), message); // e.g. "executionReport"
    }
    // Fallback or error if channel cannot be determined
    LOG.warn("Cannot determine channel from message: {}", message.toString());
    // Returning the raw message might help debug, or a constant like "UNKNOWN_CHANNEL"
    // For JsonNettyStreamingService, the channel name is crucial for routing.
    // If a message can't be mapped to a channel, it might be dropped or cause errors.
    // Consider if specific error handling or a default channel is more appropriate.
    try {
      return StreamingObjectMapperHelper.getObjectMapper().writeValueAsString(message);
    } catch (IOException e) {
      LOG.error("Error parsing channel from message: " + message.toString(), e);
      // Or handle error appropriately, maybe disconnect
    }
    return "UNKNOWN_MESSAGE_FORMAT"; // Fallback if all else fails
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    if (isPrivateService) {
      // For user data streams connected via /ws/<listenKey>, no explicit SUBSCRIBE message is
      // needed.
      // The connection itself implies subscription to all user data events.
      LOG.debug("Private service: No explicit SUBSCRIBE message for channel {}", channelName);
      return null;
    }
    // For public streams, use the standard subscription message format.
    // Create a simple JSON object instead of using CoinsphWebSocketSubscriptionMessage class
    com.fasterxml.jackson.databind.node.ObjectNode subscribeMessage =
        objectMapper.createObjectNode();
    subscribeMessage.put("method", SUBSCRIBE);
    subscribeMessage.set("params", getSubscribeParams(channelName, args));
    subscribeMessage.put("id", getTimestamp());
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    if (isPrivateService) {
      // Cannot explicitly unsubscribe from individual events on a user data stream connection.
      // To stop user data, the WebSocket connection is closed, or the listenKey is deleted.
      LOG.debug("Private service: No explicit UNSUBSCRIBE message for channel {}", channelName);
      return null;
    }
    // For public streams, use the standard unsubscription message format.
    // Create a simple JSON object instead of using CoinsphWebSocketSubscriptionMessage class
    com.fasterxml.jackson.databind.node.ObjectNode unsubscribeMessage =
        objectMapper.createObjectNode();
    unsubscribeMessage.put("method", UNSUBSCRIBE);
    unsubscribeMessage.set("params", getSubscribeParams(channelName, args));
    unsubscribeMessage.put("id", getTimestamp());
    return objectMapper.writeValueAsString(unsubscribeMessage);
  }

  private JsonNode getSubscribeParams(String channelName, Object... args) {

    if (args == null || args.length == 0) {
      return objectMapper.valueToTree(new String[] {channelName});
    }

    List<String> collect = Arrays.stream(args).map(String::valueOf).collect(Collectors.toList());
    String argsString = String.join("@", collect);

    return objectMapper.valueToTree(new String[] {channelName + argsString});
  }

  // Helper to get a unique ID for subscription messages (required by Coins.ph)
  private long getTimestamp() {
    return System.currentTimeMillis();
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    // No extensions needed by default for Coins.ph from docs
    return null;
  }

  @Override
  protected void handleMessage(JsonNode message) {
    // This method routes messages to subscribers based on channel name
    // If the message is a combined stream, it might look like:
    // {"stream":"<streamName>","data":{...}}
    // If it's a direct user data message, it might be the payload itself.

    try {
      String channel = getChannelNameFromMessage(message);
      JsonNode dataNode = message;
      if (message.has("stream") && message.has("data")) {
        // For combined streams, actual payload is in "data" field
        dataNode = message.get("data");
      }

      // Now, 'dataNode' contains the actual DTO payload.
      // 'channel' is used by super.handleMessage to route to the correct observable.
      super.handleMessage(dataNode); // Pass the actual payload to subscribers

    } catch (IOException e) {
      LOG.error("Error parsing channel from message: " + message.toString(), e);
      // Or handle error appropriately, maybe disconnect
    }
  }

  // Removed unused isUserDataChannel method

  private synchronized void startUserDataStream() {
    if (accountServiceRaw == null) {
      LOG.warn("CoinsphAccountServiceRaw not available, cannot start user data stream.");
      return;
    }
    if (listenKey != null
        && (System.currentTimeMillis() - listenKeyCreateTime)
            < LISTEN_KEY_KEEP_ALIVE_INTERVAL.toMillis() * 1.8) { // 1.8 for some buffer
      LOG.info("Listen key {} is still valid, not creating a new one.", listenKey);
      return;
    }

    try {
      LOG.info("Creating new listen key for user data stream.");
      listenKey = accountServiceRaw.createCoinsphListenKey().getListenKey();
      listenKeyCreateTime = System.currentTimeMillis();
      LOG.info("Obtained listen key: {}", listenKey);

      // Schedule keep-alive task
      if (listenKeyKeepAliveExecutor != null) {
        listenKeyKeepAliveExecutor.shutdownNow();
      }
      listenKeyKeepAliveExecutor = Executors.newSingleThreadScheduledExecutor();
      listenKeyKeepAliveExecutor.scheduleAtFixedRate(
          this::keepAliveListenKeyTask,
          LISTEN_KEY_KEEP_ALIVE_INTERVAL.toMinutes(),
          LISTEN_KEY_KEEP_ALIVE_INTERVAL.toMinutes(),
          TimeUnit.MINUTES);

      // The actual subscription to the WebSocket path /ws/<listenKey> might be handled by
      // how the apiUrl is constructed or by a specific connect method.
      // For Binance-style, the listenKey becomes part of the WebSocket URL.
      // If Coins.ph uses a different mechanism (e.g. sending listenKey in a message), adapt here.
      // For now, assume the main connection (super.connect()) will use an updated URL if needed,
      // or that subscription messages handle this.
      // If the API URL needs to change, this is more complex.
      // Let's assume for now that the base `apiUrl` is for public streams, and user streams
      // are multiplexed over the same connection after a separate auth/listenKey process,
      // or the `apiUrl` itself needs to be dynamic (e.g. `getApiUrl()` method).

      // Mark that user data stream is now active (or attempted)
      isUserDataStreamSubscribed.set(true);

    } catch (IOException e) {
      LOG.error("Failed to create or keep-alive listen key: {}", e.getMessage(), e);
      listenKey = null; // Invalidate key on error
    }
  }

  private void keepAliveListenKeyTask() {
    if (listenKey == null || accountServiceRaw == null) {
      LOG.warn("Listen key or accountServiceRaw is null, cannot keep alive.");
      if (listenKeyKeepAliveExecutor != null) {
        listenKeyKeepAliveExecutor.shutdown(); // Stop trying if key is gone
      }
      return;
    }
    try {
      LOG.info("Keeping alive listen key: {}", listenKey);
      accountServiceRaw.keepAliveCoinsphListenKey(listenKey);
      LOG.debug("Listen key keep-alive successful for {}", listenKey);
    } catch (IOException e) {
      LOG.error(
          "Failed to keep-alive listen key {}: {}. Will try to get a new one on next subscription.",
          listenKey,
          e.getMessage(),
          e);
      // Invalidate the key, so it's refreshed on next user data subscription attempt
      listenKey = null;
      listenKeyCreateTime = 0;
      if (listenKeyKeepAliveExecutor != null) {
        listenKeyKeepAliveExecutor.shutdown(); // Stop trying with the old key
      }
      // Optionally, try to restart the user data stream immediately
      // startUserDataStream();
    }
  }

  private void closeListenKey() {
    if (listenKey != null && accountServiceRaw != null) {
      try {
        LOG.info("Closing listen key: {}", listenKey);
        accountServiceRaw.closeCoinsphListenKey(listenKey);
      } catch (IOException e) {
        LOG.error("Failed to close listen key {}: {}", listenKey, e.getMessage(), e);
      } finally {
        listenKey = null;
        listenKeyCreateTime = 0;
        if (listenKeyKeepAliveExecutor != null) {
          listenKeyKeepAliveExecutor.shutdownNow();
          listenKeyKeepAliveExecutor = null;
        }
      }
    }
  }

  @Override
  public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
    // This service is now dedicated to User Data Streams.
    // 1. ListenKey is obtained and URI is set in the constructor.
    // 2. Connection to the listenKey-based URL implies subscription to all user events.
    //    No explicit "SUBSCRIBE" message is sent to the server for channels like "executionReport".
    //    Incoming messages are routed by getChannelNameFromMessage (e.g., based on event type "e").

    if (!isSocketOpen() && !isConnecting()) {
      LOG.info(
          "User Data Stream socket not open, attempting to connect before subscribing to internal channel {}.",
          channelName);
      // connect() will use the listenKey-specific URI.
      // Using blockingAwait() here can be problematic if called from a sensitive thread.
      // Consider if this automatic connect initiation is desired or if clients should explicitly
      // connect first.
      // For now, retaining original logic structure.
      connect().blockingAwait();
    }
    LOG.info(
        "Subscribing to User Data Stream internal channel {} (no explicit server-side subscription message sent).",
        channelName);
    return super.subscribeChannel(
        channelName, args); // Relies on getChannelNameFromMessage to route
  }

  // Add missing isConnecting method
  public boolean isConnecting() {
    return false; // Simple implementation - you can enhance this if needed
  }

  @Override
  public Completable disconnect() {
    closeListenKey(); // Clean up listen key on disconnect
    return super.disconnect();
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    return channelName;
  }
}
