package info.bitrich.xchangestream.bybit;

import com.fasterxml.jackson.databind.JsonNode;
import dto.BybitSubscribeMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamingService extends JsonNettyStreamingService {

  private final Logger LOG = LoggerFactory.getLogger(BybitStreamingService.class);
  public final String exchange_type;
  private final Observable<Long> pingPongSrc = Observable.interval(15, 20, TimeUnit.SECONDS);
  private Disposable pingPongSubscription;

  public BybitStreamingService(String apiUrl, Object exchange_type) {
    super(apiUrl);
    this.exchange_type = (String) exchange_type;
//    this.setEnableLoggingHandler(true);
  }

  @Override
  public Completable connect() {
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              pingPongDisconnectIfConnected();
              pingPongSubscription = pingPongSrc.subscribe(
                  o -> this.sendMessage("{\"op\":\"ping\"}"));
              completable.onComplete();
            });
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    if (message.has("topic")) {
      return message.get("topic").asText();
    }
    return "";
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    LOG.info(" getSubscribeMessage {}", channelName);
    return objectMapper.writeValueAsString(
        new BybitSubscribeMessage("subscribe", Collections.singletonList(channelName)));
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    LOG.info(" getUnsubscribeMessage {}", channelName);
    return objectMapper.writeValueAsString(
        new BybitSubscribeMessage("unsubscribe", Collections.singletonList(channelName)));
  }

  @Override
  public void messageHandler(String message) {
    LOG.debug("Received message: {}", message);
    JsonNode jsonNode;
    try {
      jsonNode = objectMapper.readTree(message);
    } catch (IOException e) {
      LOG.error("Error parsing incoming message to JSON: {}", message);
      return;
    }
    String op = "";
    boolean success = false;
    if (jsonNode.has("op")) {
      op = jsonNode.get("op").asText();
    }
    if (jsonNode.has("success")) {
      success = jsonNode.get("success").asBoolean();
    }
    if (success) {
      switch (op) {
        case "pong":
        case "subscribe":
        case "unsubscribe": {
          break;
        }
      }
      return;
    }
    handleMessage(jsonNode);
  }

  public void pingPongDisconnectIfConnected() {
    if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
      pingPongSubscription.dispose();
    }
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler.INSTANCE;
  }
}
