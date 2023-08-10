package info.bitrich.xchangestream.gateio;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.Event;
import info.bitrich.xchangestream.gateio.dto.request.GateioWebSocketRequest;
import info.bitrich.xchangestream.gateio.dto.request.payload.CurrencyPairLevelIntervalPayload;
import info.bitrich.xchangestream.gateio.dto.request.payload.CurrencyPairPayload;
import info.bitrich.xchangestream.gateio.dto.response.GateioWebSocketNotification;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.knowm.xchange.currency.CurrencyPair;

public class GateioStreamingService extends JsonNettyStreamingService {

  private static final int MAX_DEPTH_DEFAULT = 5;
  private static final int UPDATE_INTERVAL_DEFAULT = 100;

  private final Map<String, Observable<JsonNode>> subscriptions = new ConcurrentHashMap<>();

  private final ObjectMapper objectMapper = Config.getObjectMapper();

  public GateioStreamingService(String apiUri) {
    super(apiUri, Integer.MAX_VALUE);
  }


  @SneakyThrows
  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    GateioWebSocketNotification notification = objectMapper.treeToValue(message, GateioWebSocketNotification.class);
    return notification.getUniqueChannelName();
  }

  @Override
  public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
    final CurrencyPair currencyPair =
        (args.length > 0 && args[0] instanceof CurrencyPair) ? ((CurrencyPair) args[0]) : null;

    String uniqueChannelName =
        String.format("%s%s%s", channelName, Config.CHANNEL_NAME_DELIMITER, currencyPair.toString());

    // Example channel name key: spot.order_book_update-ETH_USDT, spot.trades-BTC_USDT
    if (!channels.containsKey(uniqueChannelName) && !subscriptions.containsKey(uniqueChannelName)) {
      // subscribe
      Observable<JsonNode> observable = super.subscribeChannel(uniqueChannelName, args);

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
    GateioWebSocketRequest request = getWebSocketRequest(generalChannelName, Event.SUBSCRIBE , args);
    return objectMapper.writeValueAsString(request);
  }


  private GateioWebSocketRequest getWebSocketRequest(String channelName, Event event, Object... args) {
    // create request common part
    GateioWebSocketRequest request = GateioWebSocketRequest.builder()
        .id(RandomUtils.nextLong())
        .channel(channelName)
        .event(event)
        .time(Instant.now())
        .build();

    // create channel specific payload
    Object payload;
    switch (channelName) {

      case Config.SPOT_TICKERS_CHANNEL:
      case Config.SPOT_TRADES_CHANNEL:
        payload = CurrencyPairPayload.builder()
            .currencyPair(CurrencyPair.BTC_USDT)
            .build();
        break;

      case Config.SPOT_ORDERBOOK_CHANNEL:
        Integer orderBookLevel = (args.length > 1 && args[1] instanceof Integer) ? (Integer) args[1] : MAX_DEPTH_DEFAULT;
        Duration updateSpeed = (args.length > 2 && args[2] instanceof Duration) ? (Duration) args[2] : Duration.ofMillis(UPDATE_INTERVAL_DEFAULT);

        payload = CurrencyPairLevelIntervalPayload.builder()
            .currencyPair(CurrencyPair.BTC_USDT)
            .orderBookLevel(orderBookLevel)
            .updateSpeed(updateSpeed)
            .build();
        request.setPayload(payload);
        break;

      default:
        throw new IllegalStateException("Unexpected value: " + channelName);
    }
    request.setPayload(payload);
    return request;
  }


  @SneakyThrows
  @Override
  protected void handleChannelMessage(String channel, JsonNode message) {
    // only process update events
    GateioWebSocketNotification notification = objectMapper.treeToValue(message, GateioWebSocketNotification.class);
    if (Event.UPDATE != notification.getEvent()) {
      return;
    }
    super.handleChannelMessage(channel, message);
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
    GateioWebSocketRequest unsubscribeMessage = getWebSocketRequest(generalChannelName, Event.UNSUBSCRIBE, args);
    return objectMapper.writeValueAsString(unsubscribeMessage);
  }
}
