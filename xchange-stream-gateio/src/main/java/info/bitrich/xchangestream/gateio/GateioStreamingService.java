package info.bitrich.xchangestream.gateio;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.config.IdGenerator;
import info.bitrich.xchangestream.gateio.dto.Event;
import info.bitrich.xchangestream.gateio.dto.request.GateioWsRequest;
import info.bitrich.xchangestream.gateio.dto.request.GateioWsRequest.AuthInfo;
import info.bitrich.xchangestream.gateio.dto.request.payload.CurrencyPairLevelIntervalPayload;
import info.bitrich.xchangestream.gateio.dto.request.payload.CurrencyPairPayload;
import info.bitrich.xchangestream.gateio.dto.request.payload.EmptyPayload;
import info.bitrich.xchangestream.gateio.dto.request.payload.StringPayload;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import info.bitrich.xchangestream.gateio.dto.response.balance.GateioMultipleSpotBalanceNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioMultipleUserTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioSingleUserTradeNotification;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.CurrencyPair;

@Slf4j
public class GateioStreamingService extends NettyStreamingService<GateioWsNotification> {

  private static final String USERTRADES_BROADCAST_CHANNEL_NAME = Config.SPOT_USER_TRADES_CHANNEL + Config.CHANNEL_NAME_DELIMITER + "null";

  private final Map<String, Observable<GateioWsNotification>> subscriptions = new ConcurrentHashMap<>();

  private final ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  private final String apiKey;

  private final GateioStreamingAuthHelper gateioStreamingAuthHelper;

  public GateioStreamingService(String apiUri, String apiKey, String apiSecret) {
    super(apiUri, Integer.MAX_VALUE);
    this.apiKey = apiKey;
    this.gateioStreamingAuthHelper = new GateioStreamingAuthHelper(apiSecret);
  }

  @Override
  protected String getChannelNameFromMessage(GateioWsNotification message) {
    return message.getUniqueChannelName();
  }

  @Override
  public Observable<GateioWsNotification> subscribeChannel(String channelName, Object... args) {
    final CurrencyPair currencyPair =
        (args.length > 0 && args[0] instanceof CurrencyPair) ? ((CurrencyPair) args[0]) : null;

    String uniqueChannelName =
        String.format("%s%s%s", channelName, Config.CHANNEL_NAME_DELIMITER, currencyPair);

    // Example channel name key: spot.order_book-BTC/USDT
    if (!channels.containsKey(uniqueChannelName) && !subscriptions.containsKey(uniqueChannelName)) {

      // subscribe
      Observable<GateioWsNotification> observable = super.subscribeChannel(uniqueChannelName, args);

      // cache channel subscribtion
      subscriptions.put(uniqueChannelName, observable);
    }

    return subscriptions.get(uniqueChannelName);
  }

  /**
   * Returns a JSON String containing the subscription message.
   *
   * @param uniqueChannelName e.g. spot.order_book-BTC/USDT
   * @param args CurrencyPair to subscribe and additional channel-specific arguments
   * @return subscription message
   */
  @Override
  public String getSubscribeMessage(String uniqueChannelName, Object... args) throws IOException {
    String generalChannelName = uniqueChannelName.split(Config.CHANNEL_NAME_DELIMITER)[0];
    GateioWsRequest request = getWsRequest(generalChannelName, Event.SUBSCRIBE , args);
    return objectMapper.writeValueAsString(request);
  }


  private GateioWsRequest getWsRequest(String channelName, Event event, Object... args) {
    // create request common part

    GateioWsRequest request = GateioWsRequest.builder()
        .id(IdGenerator.getInstance().requestId())
        .channel(channelName)
        .event(event)
        .time(Instant.now(Config.getInstance().getClock()))
        .build();

    // create channel specific payload
    Object payload;
    switch (channelName) {

      // channels require only currency pair in payload
      case Config.SPOT_TICKERS_CHANNEL:
      case Config.SPOT_TRADES_CHANNEL: {
        CurrencyPair currencyPair = (CurrencyPair) ArrayUtils.get(args, 0);
        Validate.notNull(currencyPair);

        payload = CurrencyPairPayload.builder()
            .currencyPair(currencyPair)
            .build();
        break;
      }

      // channel requires currency pair, level, interval in payload
      case Config.SPOT_ORDERBOOK_CHANNEL: {
        CurrencyPair currencyPair = (CurrencyPair) ArrayUtils.get(args, 0);
        Integer orderBookLevel = (Integer) ArrayUtils.get(args, 1);
        Duration updateSpeed = (Duration) ArrayUtils.get(args, 2);
        Validate.noNullElements(new Object[]{currencyPair, orderBookLevel, updateSpeed});

        payload = CurrencyPairLevelIntervalPayload.builder()
            .currencyPair(currencyPair)
            .orderBookLevel(orderBookLevel)
            .updateSpeed(updateSpeed)
            .build();
        break;
      }

      // channel requires currency pair or default value for all
      case Config.SPOT_USER_TRADES_CHANNEL: {
        CurrencyPair currencyPair = (CurrencyPair) ArrayUtils.get(args, 0);
        if (currencyPair == null) {
          payload = StringPayload.builder()
              .data("!all")
              .build();
        } else {
          payload = CurrencyPairPayload.builder()
              .currencyPair(currencyPair)
              .build();
        }
        break;
      }

      default:
        payload = EmptyPayload.builder().build();
    }

    // add auth for private channels
    if (Config.PRIVATE_CHANNELS.contains(channelName)) {
      request.setAuthInfo(AuthInfo.builder()
              .method("api_key")
              .key(apiKey)
              .sign(gateioStreamingAuthHelper.sign(channelName, event.getValue(), String.valueOf(request.getTime().getEpochSecond())))
          .build());
    }

    request.setPayload(payload);
    return request;
  }


  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler.INSTANCE;
  }


  /**
   * Returns a JSON String containing the unsubscribe message.
   *
   * @param uniqueChannelName e.g. spot.order_book-BTC/USDT
   * @param args CurrencyPair to subscribe and additional channel-specific arguments
   * @return unsubscribe message
   */
  @Override
  public String getUnsubscribeMessage(String uniqueChannelName, Object... args) throws IOException {
    String generalChannelName = uniqueChannelName.split(Config.CHANNEL_NAME_DELIMITER)[0];
    GateioWsRequest unsubscribeMessage = getWsRequest(generalChannelName, Event.UNSUBSCRIBE, args);
    return objectMapper.writeValueAsString(unsubscribeMessage);
  }

  @Override
  public void messageHandler(String message) {
    log.debug("Received message: {}", message);

    // Parse incoming message
    try {

      // process only update messages
      JsonNode jsonNode = objectMapper.readTree(message);
      String event = jsonNode.path("event") != null ? jsonNode.path("event").asText() : "";
      if (!"update".equals(event)) {
        return;
      }

      GateioWsNotification notification = objectMapper.treeToValue(jsonNode, GateioWsNotification.class);

      // process arrays in "result" field -> emit each item separately
      if (notification instanceof GateioMultipleUserTradeNotification) {
        GateioMultipleUserTradeNotification multipleNotification = (GateioMultipleUserTradeNotification) notification;
        multipleNotification.toSingleNotifications().forEach(this::handleMessage);
      }
      else if (notification instanceof GateioMultipleSpotBalanceNotification) {
        GateioMultipleSpotBalanceNotification multipleNotification = (GateioMultipleSpotBalanceNotification) notification;
        multipleNotification.toSingleNotifications().forEach(this::handleMessage);
      }
      else {
        handleMessage(notification);
      }
    } catch (IOException e) {
      log.error("Error parsing incoming message to JSON: {}", message);
    }

  }


  @Override
  protected void handleChannelMessage(String channel, GateioWsNotification message) {
    if (channel == null) {
      log.debug("Channel provided is null");
      return;
    }

    // user trade can be emitted to 2 channels
    if (message instanceof GateioSingleUserTradeNotification) {

      // subscription that listens to all currency pairs
      NettyStreamingService<GateioWsNotification>.Subscription broadcast = channels.get(
          USERTRADES_BROADCAST_CHANNEL_NAME);
      if (broadcast != null && broadcast.getEmitter() != null) {
        broadcast.getEmitter().onNext(message);
      }

      // subscription that listens to specific currency pair
      NettyStreamingService<GateioWsNotification>.Subscription specific = channels.get(channel);
      if (specific != null && specific.getEmitter() != null) {
        specific.getEmitter().onNext(message);
      }

    }
    else {
      super.handleChannelMessage(channel, message);
    }

  }
}
