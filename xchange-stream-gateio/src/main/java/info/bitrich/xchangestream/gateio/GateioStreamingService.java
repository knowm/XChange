package info.bitrich.xchangestream.gateio;


import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.Event;
import info.bitrich.xchangestream.gateio.dto.request.GateioWebSocketRequest;
import info.bitrich.xchangestream.gateio.dto.request.payload.CurrencyPairLevelIntervalPayload;
import info.bitrich.xchangestream.gateio.dto.request.payload.CurrencyPairPayload;
import info.bitrich.xchangestream.gateio.dto.response.GateioWebSocketNotification;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.CurrencyPair;

@Slf4j
public class GateioStreamingService extends NettyStreamingService<GateioWebSocketNotification> {

  private final Map<String, Observable<GateioWebSocketNotification>> subscriptions = new ConcurrentHashMap<>();

  private final ObjectMapper objectMapper = Config.getObjectMapper();

  public GateioStreamingService(String apiUri) {
    super(apiUri, Integer.MAX_VALUE);
  }

  @Override
  protected String getChannelNameFromMessage(GateioWebSocketNotification message) {
    return message.getUniqueChannelName();
  }

  @Override
  public Observable<GateioWebSocketNotification> subscribeChannel(String channelName, Object... args) {
    final CurrencyPair currencyPair =
        (args.length > 0 && args[0] instanceof CurrencyPair) ? ((CurrencyPair) args[0]) : null;

    String uniqueChannelName =
        String.format("%s%s%s", channelName, Config.CHANNEL_NAME_DELIMITER, currencyPair.toString());

    // Example channel name key: spot.order_book-BTC/USDT
    if (!channels.containsKey(uniqueChannelName) && !subscriptions.containsKey(uniqueChannelName)) {

      // subscribe
      Observable<GateioWebSocketNotification> observable = super.subscribeChannel(uniqueChannelName, args);

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
      case Config.SPOT_TRADES_CHANNEL: {
        CurrencyPair currencyPair = (CurrencyPair) ArrayUtils.get(args, 0);
        Validate.notNull(currencyPair);

        payload = CurrencyPairPayload.builder()
            .currencyPair(currencyPair)
            .build();
        break;
      }

      case Config.SPOT_ORDERBOOK_CHANNEL: {
        CurrencyPair currencyPair = (CurrencyPair) ArrayUtils.get(args, 0);
        Integer orderBookLevel = (Integer) ArrayUtils.get(args, 1);
        Duration updateSpeed = (Duration) ArrayUtils.get(args, 2);
        Validate.noNullElements(List.of(currencyPair, orderBookLevel, updateSpeed));

        payload = CurrencyPairLevelIntervalPayload.builder()
            .currencyPair(currencyPair)
            .orderBookLevel(orderBookLevel)
            .updateSpeed(updateSpeed)
            .build();
        break;
      }

      default:
        throw new IllegalStateException("Unexpected value: " + channelName);
    }
    request.setPayload(payload);
    return request;
  }


  @SneakyThrows
  @Override
  protected void handleChannelMessage(String channel, GateioWebSocketNotification notification) {
    // only process update events
    if (Event.UPDATE != notification.getEvent()) {
      return;
    }
    super.handleChannelMessage(channel, notification);
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

  @Override
  public void messageHandler(String message) {
    log.debug("Received message: {}", message);

    // Parse incoming message
    try {
      GateioWebSocketNotification notification = objectMapper.readValue(message, GateioWebSocketNotification.class);
      handleMessage(notification);
    } catch (IOException e) {
      log.error("Error parsing incoming message to JSON: {}", message);
    }

  }

}
