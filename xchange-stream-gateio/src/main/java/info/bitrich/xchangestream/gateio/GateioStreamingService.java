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
  private static final String CHANNEL_NAME_DELIMITER = "-";

  private static final int MAX_DEPTH_DEFAULT = 5;
  private static final int UPDATE_INTERVAL_DEFAULT = 100;

  private final Map<String, Observable<JsonNode>> subscriptions = new ConcurrentHashMap<>();

  private final ObjectMapper objectMapper = Config.getObjectMapper();

  public GateioStreamingService(String apiUri) {
    super(apiUri, Integer.MAX_VALUE);
  }


  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    String channel = message.path("channel") != null ? message.path("channel").asText() : "";
    String currencyPairOrderBook =
        message.path("result").path("s") != null ? message.path("result").path("s").asText() : "";
    String currencyPairTradesTickers =
        message.path("result").path("currency_pair") != null
            ? message.path("result").path("currency_pair").asText()
            : "";

    return new StringBuilder(channel)
        .append(CHANNEL_NAME_DELIMITER)
        .append(currencyPairOrderBook)
        .append(currencyPairTradesTickers)
        .toString();
  }

  @Override
  public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
    final CurrencyPair currencyPair =
        (args.length > 0 && args[0] instanceof CurrencyPair) ? ((CurrencyPair) args[0]) : null;

    String currencyPairChannelName =
        String.format("%s-%s", channelName, currencyPair.toString().replace('/', '_'));

    // Example channel name key: spot.order_book_update-ETH_USDT, spot.trades-BTC_USDT
    if (!channels.containsKey(currencyPairChannelName) && !subscriptions.containsKey(currencyPairChannelName)) {
      // subscribe
      Observable<JsonNode> observable = super.subscribeChannel(currencyPairChannelName, args);

      // cache channel subscribtion
      subscriptions.put(currencyPairChannelName, observable);
    }

    return subscriptions.get(currencyPairChannelName);
  }

  /**
   * Returns a JSON String containing the subscription message.
   *
   * @param channelName e.g. spot.order_book_update-ETH_USDT
   * @param args CurrencyPair to subscribe and additional channel-specific arguments
   * @return subscription message
   */
  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    GateioWebSocketRequest request = getWebSocketRequest(channelName, Event.SUBSCRIBE , args);
    return objectMapper.writeValueAsString(request);
  }


  private GateioWebSocketRequest getWebSocketRequest(String channelName, Event event, Object... args) {
    // create request common part
    String generalChannelName = channelName.split(CHANNEL_NAME_DELIMITER)[0];
    GateioWebSocketRequest request = GateioWebSocketRequest.builder()
        .id(RandomUtils.nextLong())
        .channel(generalChannelName)
        .event(event)
        .time(Instant.now())
        .build();

    // create channel specific payload
    Object payload;
    switch (generalChannelName) {

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
        throw new IllegalStateException("Unexpected value: " + generalChannelName);
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

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    GateioWebSocketRequest unsubscribeMessage = getWebSocketRequest(channelName, Event.UNSUBSCRIBE, args);
    return objectMapper.writeValueAsString(unsubscribeMessage);
  }
}
