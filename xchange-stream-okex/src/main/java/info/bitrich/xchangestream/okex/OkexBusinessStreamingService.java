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
import lombok.Setter;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static info.bitrich.xchangestream.core.StreamingExchange.*;

public class OkexBusinessStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(OkexBusinessStreamingService.class);

  protected static final String SUBSCRIBE = "subscribe";
  protected static final String UNSUBSCRIBE = "unsubscribe";
  @Setter
  private WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler = null;
  private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);
  private Disposable pingPongSubscription;

  public OkexBusinessStreamingService(String apiUrl, ExchangeSpecification exchangeSpecification) {
    super(
        apiUrl,
        65536,
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_CONNECTION_TIMEOUT),
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_RETRY_DURATION),
        (Integer) exchangeSpecification.getExchangeSpecificParametersItem(WS_IDLE_TIMEOUT));
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
        // ping pong message
        return;
      }
      LOG.error("Error parsing incoming message to JSON: {}", message);
      return;
    }
    if (jsonNode.get("event") != null && jsonNode.get("event").asText().equals("subscribe")) {
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
            message.get("arg").get("channel").asText() + "-" + message.get("arg").get("instId").asText();
      }
    }
    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return objectMapper.writeValueAsString(
        new OkexSubscribeMessage<>("", SUBSCRIBE, Collections.singletonList(getTopic(channelName))));
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return objectMapper.writeValueAsString(
        new OkexSubscribeMessage<>(
            "", UNSUBSCRIBE, Collections.singletonList(getTopic(channelName))));
  }

  private OkexSubscriptionTopic getTopic(String channelName) {
    int separatorIndex = channelName.indexOf('-');
    String okexChannels = channelName.substring(0, separatorIndex);
    String instrument = channelName.substring(separatorIndex + 1);
    return new OkexSubscriptionTopic(okexChannels, null, null, instrument);
  }

  @Override
  protected WebSocketClientHandler getWebSocketClientHandler(
      WebSocketClientHandshaker handshake, WebSocketClientHandler.WebSocketMessageHandler handler) {
    LOG.info("Registering OkxWebSocketClientHandler");
    return new OkxWebSocketClientHandler(handshake, handler);
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
