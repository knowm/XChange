package info.bitrich.xchangestream.bitso;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitso.dto.BitsoTradsTransaction;
import info.bitrich.xchangestream.bitso.dto.BitsoWebSocketTransaction;
import info.bitrich.xchangestream.bitso.dto.BitsoWebsocketAuthData;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextHandler;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitsoStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(BitsoStreamingService.class);
  private static final String SUBSCRIBE = "subscribe";
  private static final String UNSUBSCRIBE = "unsubscribe";
  private static final String SHARE_CHANNEL_NAME = "ALL";
  private final Map<String, Observable<JsonNode>> subscriptions = new ConcurrentHashMap<>();
  private ProductSubscription product = null;
//  private final Supplier<BitsoWebsocketAuthData> authData;
  private final boolean subscribeL3Orderbook;

  private WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler = null;

//  public BitsoStreamingService(
//          String apiUrl,
//          Supplier<BitsoWebsocketAuthData> authData,
//          boolean subscribeL3Orderbook) {
//    super(apiUrl, Integer.MAX_VALUE);
//    this.authData = authData;
//    this.subscribeL3Orderbook = subscribeL3Orderbook;
//  }
  public BitsoStreamingService(
          String apiUrl,
          boolean subscribeL3Orderbook) {
    super(apiUrl, Integer.MAX_VALUE);
//    this.authData = authData;
    this.subscribeL3Orderbook = subscribeL3Orderbook;
  }

  public BitsoStreamingService(
      String apiUrl,
      int maxFramePayloadLength,
      Duration connectionTimeout,
      Duration retryDuration,
      int idleTimeoutSeconds,
      Supplier<BitsoWebsocketAuthData> authData,
      boolean subscribeL3Orderbook) {
    super(apiUrl, maxFramePayloadLength, connectionTimeout, retryDuration, idleTimeoutSeconds);
//    this.authData = authData;
    this.subscribeL3Orderbook = subscribeL3Orderbook;
  }

  public ProductSubscription getProduct() {
    return product;
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    return SHARE_CHANNEL_NAME;
  }

  /**
   * Subscribes to the provided channel name, maintains a cache of subscriptions, in order not to
   * subscribe more than once to the same channel.
   *
   * @param channelName the name of the requested channel.
   * @return an Observable of json objects coming from the exchange.
   */
  @Override
  public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
//    String symbol=channelName.replace("/","_").toLowerCase();
    if(args[0] == null){
      throw new UnsupportedOperationException("args could not null");
    }
    System.out.println("args of channel value is..."+args[0]);
    channelName = " {\"action\":\"subscribe\",\"book\":\""+channelName+"\",\"type\":\""+args[0]+"\"}";
//    channelName = " {\"action\":\"subscribe\",\"book\":\""+channelName+"\",\"type\":\"orders\"}";
    System.out.println(channelName);
    if (!channels.containsKey(channelName) && !subscriptions.containsKey(channelName)) {
      subscriptions.put(channelName, super.subscribeChannel(channelName, args));
    }

    return subscriptions.get(channelName);
  }

  /**
   * Subscribes to web socket transactions related to the specified currency, in their raw format.
   *
   * @param currencyPair The currency pair.
   * @return The stream.
   */
  public Observable<BitsoWebSocketTransaction> getRawWebSocketTransactions(
          CurrencyPair currencyPair, Object... args) {
    String channelName = currencyPair.base.toString() + "_" + currencyPair.counter.toString();
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    return subscribeChannel(channelName.toLowerCase(),args)
            .map(s -> mapper.readValue(s.toString(), BitsoWebSocketTransaction.class))
            .filter(t -> {
              if(!ObjectUtils.isEmpty(t.getResponse())) {
                if (t.getResponse().equals("ok")) {
                  return true;
                }
              }else if((channelName.toLowerCase()).equals(t.getBook())){
                return true;
              }
              return false;
            })
            .filter(t -> !isNullOrEmpty(t.getEventType()));
  }

  /**
   * Subscribes to web socket transactions related to the specified currency, in their raw format.
   *
   * @param currencyPair The currency pair.
   * @return The stream.
   */
  public Observable<BitsoTradsTransaction> getRawTradesTransactions(
          CurrencyPair currencyPair, Object... args) {
    String channelName = currencyPair.base.toString() + "_" + currencyPair.counter.toString();
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    return subscribeChannel(channelName.toLowerCase(),args)
            .map(s -> mapper.readValue(s.toString(), BitsoTradsTransaction.class))
            .filter(t -> {
              if(!ObjectUtils.isEmpty(t.getResponse())) {
                if (t.getResponse().equals("ok")) {
                  return true;
                }
              }else if((channelName.toLowerCase()).equals(t.getBook())){
                return true;
              }
              return false;
            })
            .filter(t -> !isNullOrEmpty(t.getEventType()));
  }

  boolean isAuthenticated() {
//    return authData.get() != null;
    return false;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    return SHARE_CHANNEL_NAME;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
//    BitsoWebSocketSubscriptionMessage subscribeMessage =
////        new BitsoWebSocketSubscriptionMessage(SUBSCRIBE, product, subscribeL3Orderbook, authData.get());
//    new BitsoWebSocketSubscriptionMessage(SUBSCRIBE, product, subscribeL3Orderbook, null);
//    return objectMapper.writeValueAsString(subscribeMessage);
    return channelName;
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
//    BitsoWebSocketSubscriptionMessage subscribeMessage =
////        new BitsoWebSocketSubscriptionMessage(UNSUBSCRIBE, new String[] {"level2", "matches", "ticker", "full"}, authData.get());
//    new BitsoWebSocketSubscriptionMessage(UNSUBSCRIBE, new String[] {"level2", "matches", "ticker", "full"}, null);
//    return objectMapper.writeValueAsString(subscribeMessage);
    return channelName;
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
  }

  @Override
  protected WebSocketClientHandler getWebSocketClientHandler(
      WebSocketClientHandshaker handshaker,
      WebSocketClientHandler.WebSocketMessageHandler handler) {
    LOG.info("Registering CoinbaseProWebSocketClientHandler");
    return new CoinbaseProWebSocketClientHandler(handshaker, handler);
  }

  public void setChannelInactiveHandler(
      WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler) {
    this.channelInactiveHandler = channelInactiveHandler;
  }

  public void subscribeMultipleCurrencyPairs(ProductSubscription... products) {
    this.product = products[0];
  }

  @Override
  protected void handleChannelMessage(String channel, JsonNode message) {
    if (SHARE_CHANNEL_NAME.equals(channel)) {
//      channels.forEach((k, v) -> v.getEmitter().onNext(message));
      Iterator<Map.Entry<String, Subscription>> iterator = channels.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, Subscription> entry = iterator.next();
        entry.getValue().getEmitter().onNext(message);
      }

    } else {
      super.handleChannelMessage(channel, message);
    }
  }

  /**
   * Custom client handler in order to execute an external, user-provided handler on channel events.
   * This is useful because it seems CoinbasePro unexpectedly closes the web socket connection.
   */
  class CoinbaseProWebSocketClientHandler extends NettyWebSocketClientHandler {

    public CoinbaseProWebSocketClientHandler(
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
