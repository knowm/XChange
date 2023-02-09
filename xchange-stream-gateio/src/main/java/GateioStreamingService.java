import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GateioWebSocketSubscriptionMessage;
import dto.response.GateioOrderBookResponse;
import dto.response.GateioTradesResponse;
import dto.response.GateioWebSocketTransaction;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.service.exception.NotConnectedException;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/** Author: Max Gao (gaamox@tutanota.com) Created: 05-05-2021 */
public class GateioStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(GateioStreamingService.class);
  private static final String SUBSCRIBE = "subscribe";
  private static final String UNSUBSCRIBE = "unsubscribe";
  private static final String CHANNEL_NAME_DELIMITER = "-";

  public static final String SPOT_ORDERBOOK_CHANNEL = "spot.order_book";
  public static final String SPOT_TRADES_CHANNEL = "spot.trades";
  public static final String SPOT_TICKERS_CHANNEL = "spot.tickers";

  private static final int MAX_DEPTH_DEFAULT = 5;
  private static final int UPDATE_INTERVAL_DEFAULT = 100;

  private final String apiUri;
  private ProductSubscription productSubscription;
  private ExchangeSpecification exchangeSpecification;

  private final Map<String, Observable<JsonNode>> subscriptions = new ConcurrentHashMap<>();
  private final Map<String, String> channelSubscriptionMessages = new ConcurrentHashMap<>();

  public GateioStreamingService(String apiUri, ExchangeSpecification exchangeSpecification) {
    super(apiUri, Integer.MAX_VALUE);
    this.apiUri = apiUri;
    this.exchangeSpecification = exchangeSpecification;
  }

  public Observable<GateioWebSocketTransaction> getRawWebSocketTransactions(
      CurrencyPair currencyPair, String channelName, Object... args) {
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    return subscribeChannel(channelName, currencyPair, args)
        .map(
            msg -> {
              switch (channelName) {
                case SPOT_ORDERBOOK_CHANNEL:
                  return mapper.treeToValue(msg, GateioOrderBookResponse.class);
                case SPOT_TRADES_CHANNEL:
                  return mapper.treeToValue(msg, GateioTradesResponse.class);
              }
              return mapper.treeToValue(msg, GateioWebSocketTransaction.class);
            })
        .filter(t -> currencyPair.equals(t.getCurrencyPair()));
  }

  public void subscribeMultipleCurrencyPairs(ProductSubscription... products) {
    this.productSubscription = products[0];
  }

  public ProductSubscription getProduct() {
    return this.productSubscription;
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
    Set<CurrencyPair> pairs = extractPairsFromArgs(args);
    String concatenatedPairNames = pairs.stream()
            .map(CurrencyPair::toString)
            .map(name -> name.replace('/', '_'))
            .collect(Collectors.joining("-"));

    String currencyPairChannelName = String.format("%s-%s", channelName, concatenatedPairNames);

    try {
      // Example channel name key: spot.order_book_update-ETH_USDT, spot.trades-BTC_USDT, spot.tickers-ETH_USDT-BTC_USDT
      if (!channels.containsKey(currencyPairChannelName)
          && !subscriptions.containsKey(currencyPairChannelName)) {
        if(channelName.equals(GateioStreamingService.SPOT_TICKERS_CHANNEL)) {
          subscriptions.put(currencyPairChannelName, subscribeChannelWithoutDispose(currencyPairChannelName, args));
        } else {
          subscriptions.put(currencyPairChannelName, super.subscribeChannel(currencyPairChannelName, args));
        }

        channelSubscriptionMessages.put(
            currencyPairChannelName, getSubscribeMessage(currencyPairChannelName, pairs.toArray(new Object[0])));
      }
    } catch (IOException e) {
      LOG.error("Failed to subscribe to channel: {}", currencyPairChannelName);
    }

    return subscriptions.get(currencyPairChannelName);
  }

  private Observable<JsonNode> subscribeChannelWithoutDispose(String channelName, Object... args) {
    final String channelId = getSubscriptionUniqueId(channelName, args);
    LOG.info("Subscribing to channel {}", channelId);

    return Observable.<JsonNode>create(e -> {
      if (!isSocketOpen()) {
        e.onError(new NotConnectedException());
      }
      channels.computeIfAbsent(channelId, cid -> {
        Subscription newSubscription = new Subscription(e, channelName, args);
        try {
          sendMessage(getSubscribeMessage(channelName, args));
        } catch (Exception throwable) {
          e.onError(throwable);
        }
        return newSubscription;
      });
    }).share();
  }

  private Set<CurrencyPair> extractPairsFromArgs(Object... args) {
      return Arrays.stream(args)
              .filter(obj -> obj instanceof CurrencyPair)
              .map(obj -> (CurrencyPair) obj)
              .collect(Collectors.toSet());
  }

  /**
   * Returns a JSON String containing the subscription message.
   *
   * @param channelName
   * @param args CurrencyPairs to subscribe to
   * @return
   * @throws IOException
   */
  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    Set<CurrencyPair> pairs = extractPairsFromArgs(args);

    final int maxDepth =
        exchangeSpecification.getExchangeSpecificParametersItem("maxDepth") != null
            ? (int) exchangeSpecification.getExchangeSpecificParametersItem("maxDepth")
            : MAX_DEPTH_DEFAULT;
    final int msgInterval =
        exchangeSpecification.getExchangeSpecificParametersItem("updateInterval") != null
            ? (int) exchangeSpecification.getExchangeSpecificParametersItem("updateInterval")
            : UPDATE_INTERVAL_DEFAULT;

    GateioWebSocketSubscriptionMessage subscribeMessage = pairs.size() > 1 ?
            new GateioWebSocketSubscriptionMessage(
                    channelName.split(CHANNEL_NAME_DELIMITER)[0],
                    SUBSCRIBE,
                    pairs) :
            new GateioWebSocketSubscriptionMessage(
                    channelName.split(CHANNEL_NAME_DELIMITER)[0],
                    SUBSCRIBE,
                    pairs.iterator().next(),
                    msgInterval,
                    maxDepth);

    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler.INSTANCE;
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    GateioWebSocketSubscriptionMessage unsubscribeMessage;
    if (channelName.equals(GateioStreamingService.SPOT_TICKERS_CHANNEL)) {
      unsubscribeMessage = new GateioWebSocketSubscriptionMessage(
              GateioStreamingService.SPOT_TICKERS_CHANNEL,
              UNSUBSCRIBE,
              (CurrencyPair) args[0],
              null,
              null);
    } else {
      unsubscribeMessage =
              objectMapper.readValue(
                      channelSubscriptionMessages.get(channelName), GateioWebSocketSubscriptionMessage.class);
      unsubscribeMessage.setEvent(UNSUBSCRIBE);
    }

    return objectMapper.writeValueAsString(unsubscribeMessage);
  }

  @Override
  protected void handleChannelMessage(String channel, JsonNode message) {
    if (channel == null) {
      LOG.debug("Channel provided is null");
      return;
    }

    Optional<String> channelOptional;
    String[] channelParts = channel.split("-");
    if(channelParts.length > 1) {
      String channelName = channelParts[0];
      String pair = channelParts[1];
      channelOptional = channels.keySet().stream()
              .filter(key -> key.startsWith(channelName) && key.contains(pair))
              .findAny();
    } else {
      channelOptional = Optional.of(channel);
    }

    if (channelOptional.isPresent()) {
      NettyStreamingService<JsonNode>.Subscription subscription = channels.get(channelOptional.get());
      if (subscription == null) {
        LOG.debug("Channel has been closed {}.", channel);
        return;
      }

      ObservableEmitter<JsonNode> emitter = subscription.getEmitter();
      if (emitter == null) {
        LOG.debug("No subscriber for channel {}.", channel);
        return;
      }

      emitter.onNext(message);
    } else {
      LOG.debug("Channel not found {}.", channel);
    }
  }
}
