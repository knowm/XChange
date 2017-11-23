package info.bitrich.xchangestream.gdax;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gdax.dto.GDAXWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.gdax.netty.WebSocketClientCompressionAllowClientNoContextHandler;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static info.bitrich.xchangestream.gdax.dto.GDAXWebSocketSubscriptionMessage.PRODUCT_ID;
import static info.bitrich.xchangestream.gdax.dto.GDAXWebSocketSubscriptionMessage.PRODUCT_IDS;

public class GDAXStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(GDAXStreamingService.class);
  private static final String SUBSCRIBE = "subscribe";
  private static final String UNSUBSCRIBE = "unsubscribe";
  private static final String SHARE_CHANNEL_NAME = "ALL";
  private final Map<String, Observable<JsonNode>> subscriptions = new HashMap<>();
  private CurrencyPair[] currencyPairs = null;
  
  private WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler = null;

  public GDAXStreamingService(String apiUrl) {
    super(apiUrl, Integer.MAX_VALUE);
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    if (this.currencyPairs != null) return SHARE_CHANNEL_NAME;
    else return channelName;
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
    if (this.currencyPairs != null) channelName = SHARE_CHANNEL_NAME;

    if (!channels.containsKey(channelName) && !subscriptions.containsKey(channelName)){
      subscriptions.put(channelName, super.subscribeChannel(channelName, args));
    }
    
    return subscriptions.get(channelName);
  }
  
  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    if (this.currencyPairs != null) {
      return SHARE_CHANNEL_NAME;
    } else if (message.has(PRODUCT_ID)) {
      return message.get(PRODUCT_ID).asText();
    } else {
      return message.get("channels").get(0).get(PRODUCT_IDS).get(0).asText();
    }
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    GDAXWebSocketSubscriptionMessage subscribeMessage;
    if (currencyPairs != null) {
      String[] strCurrencyPairs = new String[currencyPairs.length];
      for (int i = 0; i < currencyPairs.length; i++) {
        strCurrencyPairs[i] = currencyPairs[i].base.toString() + "-" + currencyPairs[i].counter.toString();
      }
      subscribeMessage = new GDAXWebSocketSubscriptionMessage(SUBSCRIBE, strCurrencyPairs);
    } else subscribeMessage = new GDAXWebSocketSubscriptionMessage(SUBSCRIBE, channelName);
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    GDAXWebSocketSubscriptionMessage subscribeMessage =
      new GDAXWebSocketSubscriptionMessage(UNSUBSCRIBE, channelName);
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    super.handleMessage(message);
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
  }
  
  @Override
  protected WebSocketClientHandler getWebSocketClientHandler(WebSocketClientHandshaker handshaker, 
                                                             WebSocketClientHandler.WebSocketMessageHandler handler) {
    LOG.info("Registering GDAXWebSocketClientHandler");
    return new GDAXWebSocketClientHandler(handshaker, handler);
  }
  
  public void setChannelInactiveHandler(WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler) {
    this.channelInactiveHandler = channelInactiveHandler;
  }

  public void subscribeMultipleCurrencyPairs(CurrencyPair[] currencyPairs) {
    this.currencyPairs = currencyPairs;
  }

  /**
   * Custom client handler in order to execute an external, user-provided handler on channel events.
   * This is useful because it seems GDAX unexpectedly closes the web socket connection.
   */
  class GDAXWebSocketClientHandler extends  WebSocketClientHandler{

    public GDAXWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
      super(handshaker, handler);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
      super.channelInactive(ctx);
      if (channelInactiveHandler != null){
        channelInactiveHandler.onMessage("WebSocket Client disconnected!");
      }
    }
  }
}
