package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.okex.dto.OkexSubscribeMessage;
import info.bitrich.xchangestream.okex.dto.OkexSubscriptionTopic;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class OkexStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(OkexStreamingService.class);

  protected static final String SUBSCRIBE = "subscribe";
  protected static final String UNSUBSCRIBE = "unsubscribe";

  public static final String TRADES = "trades";
  public static final String ORDERBOOK = "books";
  public static final String ORDERBOOK5 = "books5";
  public static final String FUNDING_RATE = "funding-rate";
  public static final String TICKERS = "tickers";

  private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);

  private WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler = null;

  private Disposable pingPongSubscription;

  private final ExchangeSpecification xSpec;

  public OkexStreamingService(String apiUrl, ExchangeSpecification exchangeSpecification) {
    super(apiUrl);
    this.xSpec = exchangeSpecification;
  }

  @Override
  public Completable connect() {
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              try {
                if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
                  pingPongSubscription.dispose();
                }
                pingPongSubscription = pingPongSrc.subscribe(o -> this.sendMessage("ping"));
                completable.onComplete();
              } catch (Exception e) {
                completable.onError(e);
              }
            });
  }

  @Override
  public void messageHandler(String message) {
    LOG.debug("Received message: {}", message);
    JsonNode jsonNode;

    // Parse incoming message to JSON
    try {
      jsonNode = objectMapper.readTree(message);
    } catch (IOException e) {
      if ("pong".equals(message)) {
          LOG.info("Received pong message: {}", message);
        return;
      }
      LOG.error("Error parsing incoming message to JSON: {}", message);
      return;
    }
    if (processArrayMessageSeparately() && jsonNode.isArray()) {
      // In case of array - handle every message separately.
      for (JsonNode node : jsonNode) {
        handleMessage(node);
      }
    } else {
      handleMessage(jsonNode);
    }
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    String channelName = "";
    if (message.has("arg")) {
      if (message.get("arg").has("channel") && message.get("arg").has("instId")) {
        channelName =
            message.get("arg").get("channel").asText() + message.get("arg").get("instId").asText();
      }
    }
    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return objectMapper.writeValueAsString(
        new OkexSubscribeMessage("", SUBSCRIBE, Collections.singletonList(getTopic(channelName))));
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return objectMapper.writeValueAsString(
        new OkexSubscribeMessage<>(
            "", UNSUBSCRIBE, Collections.singletonList(getTopic(channelName))));
  }

  private OkexSubscriptionTopic getTopic(String channelName) {
    if (channelName.contains(ORDERBOOK5)) {
      return new OkexSubscriptionTopic(ORDERBOOK5, null, null, channelName.replace(ORDERBOOK5, ""));
    } else if (channelName.contains(ORDERBOOK)) {
      return new OkexSubscriptionTopic(ORDERBOOK, null, null, channelName.replace(ORDERBOOK, ""));
    } else if (channelName.contains(TRADES)) {
      return new OkexSubscriptionTopic(TRADES, null, null, channelName.replace(TRADES, ""));
    } else if (channelName.contains(TICKERS)) {
      return new OkexSubscriptionTopic(TICKERS, null, null, channelName.replace(TICKERS, ""));
    } else if (channelName.contains(FUNDING_RATE)) {
      return new OkexSubscriptionTopic(
          FUNDING_RATE, null, null, channelName.replace(FUNDING_RATE, ""));
    } else {
      throw new NotYetImplementedForExchangeException(
          "ChannelName: "
              + channelName
              + " has not implemented yet on "
              + this.getClass().getSimpleName());
    }
  }

  @Override
  protected WebSocketClientHandler getWebSocketClientHandler(
      WebSocketClientHandshaker handshake, WebSocketClientHandler.WebSocketMessageHandler handler) {
    LOG.info("Registering OkxWebSocketClientHandler");
    return new OkxWebSocketClientHandler(handshake, handler);
  }

  public void setChannelInactiveHandler(
      WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler) {
    this.channelInactiveHandler = channelInactiveHandler;
  }

  /**
   * Custom client handler in order to execute an external, user-provided handler on channel events.
   */
  class OkxWebSocketClientHandler extends NettyWebSocketClientHandler {

    public OkxWebSocketClientHandler(
        WebSocketClientHandshaker handshake, WebSocketMessageHandler handler) {
      super(handshake, handler);
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

  public void pingPongDisconnectIfConnected() {
    if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
      pingPongSubscription.dispose();
    }
  }
}
