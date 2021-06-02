package info.bitrich.xchangestream.dydx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.dydx.dto.dydxWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.dydx.dto.dydxWebSocketTransaction;
import info.bitrich.xchangestream.dydx.dto.v3.dydxInitialOrderBookMessage;
import info.bitrich.xchangestream.dydx.dto.v3.dydxUpdateOrderBookMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Author: Max Gao (gaamox@tutanota.com) Created: 20-02-2021 */
public class dydxStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(dydxStreamingService.class);
  private static final String SUBSCRIBE = "subscribe";
  private static final String UNSUBSCRIBE = "unsubscribe";
  private static final String CHANNEL = "channel";
  private static final String ID = "id";

  public static final String SUBSCRIBED = "subscribed";
  public static final String CHANNEL_DATA = "channel_data";

  public static final String V3_ORDERBOOK = "v3_orderbook";
  public static final String V3_TRADES = "v3_trades";
  public static final String V3_ACCOUNTS = "v3_accounts";
  public static final String V3_MARKETS = "v3_markets";

  public static final String V1_ORDERBOOK = "orderbook";
  public static final String V1_TRADES = "trades";
  public static final String V1_ACCOUNTS = "accounts";
  public static final String V1_MARKETS = "markets";

  public static final String ORDERBOOK = "orderbook";

  private final String apiUri;
  private ProductSubscription productSubscription;

  private final Map<String, Observable<JsonNode>> subscriptions = new ConcurrentHashMap<>();

  public dydxStreamingService(String apiUri) {
    super(apiUri, Integer.MAX_VALUE);
    this.apiUri = apiUri;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    return message.has(CHANNEL) && message.has(ID)
        ? String.format("%s-%s", message.get(CHANNEL).asText(), message.get(ID).asText())
        : "";
  }

  public ProductSubscription getProduct() {
    return productSubscription;
  }

  public void subscribeMultipleCurrencyPairs(ProductSubscription... products) {
    this.productSubscription = products[0];
  }

  /**
   * Creates an observable of a channel using the baseChannelName and currencyPair. For example,
   * subscribing to the trades channel for WETH/USDC will create a new channel "trades-WETH-USDC".
   *
   * @param currencyPair any currency pair supported by dydx
   * @param baseChannelName e.g. "orderbook", "v3_orderbook", etc.
   * @return
   */
  public Observable<dydxWebSocketTransaction> getRawWebsocketTransactions(
      CurrencyPair currencyPair, String baseChannelName) {
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    String currencyPairChannelName =
        String.format("%s-%s", baseChannelName, currencyPair.toString().replace('/', '-'));

    return subscribeChannel(currencyPairChannelName, currencyPair)
        .map(
            msg -> {
              switch (baseChannelName) {
                case V1_ORDERBOOK:
                case V3_ORDERBOOK:
                  return handleOrderbookMessage(currencyPairChannelName, objectMapper, msg);
              }

              return mapper.readValue(msg.toString(), dydxWebSocketTransaction.class);
            })
        .filter(t -> currencyPair.equals(new CurrencyPair(t.getId())))
        .filter(t -> baseChannelName.equals(t.getChannel()));
  }

  private dydxWebSocketTransaction handleOrderbookMessage(
      String orderBookChannel, ObjectMapper mapper, JsonNode msg) throws Exception {
    if (orderBookChannel.contains(V3_ORDERBOOK)) {
      switch (msg.get("type").asText()) {
        case SUBSCRIBED:
          return mapper.readValue(msg.toString(), dydxInitialOrderBookMessage.class);
        case CHANNEL_DATA:
          return mapper.readValue(msg.toString(), dydxUpdateOrderBookMessage.class);
      }
    }
    if (orderBookChannel.contains(V1_ORDERBOOK)) {
      switch (msg.get("type").asText()) {
        case SUBSCRIBED:
          return mapper.readValue(
              msg.toString(),
              info.bitrich.xchangestream.dydx.dto.v1.dydxInitialOrderBookMessage.class);
        case CHANNEL_DATA:
          return mapper.readValue(
              msg.toString(),
              info.bitrich.xchangestream.dydx.dto.v1.dydxUpdateOrderBookMessage.class);
      }
    }
    return mapper.readValue(msg.toString(), dydxWebSocketTransaction.class);
  }

  @Override
  public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
    if (!channels.containsKey(channelName) && !subscriptions.containsKey(channelName)) {
      subscriptions.put(channelName, super.subscribeChannel(channelName, args));
    }

    return subscriptions.get(channelName);
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    final CurrencyPair currencyPair =
        (args.length > 0 && args[0] instanceof CurrencyPair) ? ((CurrencyPair) args[0]) : null;

    if (channelName.contains(ORDERBOOK)) {
      if (productSubscription != null && productSubscription.getOrderBook() != null) {
        return objectMapper.writeValueAsString(
            new dydxWebSocketSubscriptionMessage(
                SUBSCRIBE,
                channelName.contains(V3_ORDERBOOK) ? V3_ORDERBOOK : ORDERBOOK,
                currencyPair.toString().replace('/', '-')));
      }
    }
    return null;
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return null;
  }
}
