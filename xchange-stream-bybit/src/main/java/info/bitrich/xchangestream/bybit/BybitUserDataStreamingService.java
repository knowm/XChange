package info.bitrich.xchangestream.bybit;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import dto.BybitSubscribeMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextHandler;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitUserDataStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(BybitUserDataStreamingService.class);
  private Disposable pingPongSubscription;
  private final Observable<Long> pingPongSrc = Observable.interval(15, 20, TimeUnit.SECONDS);
  private final ExchangeSpecification spec;
  @Getter
  private boolean isAuthorized = false;
  @Setter
  private WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler = null;


  public BybitUserDataStreamingService(String url, ExchangeSpecification spec) {
    super(url);
    this.spec = spec;
   // this.setEnableLoggingHandler(true);
  }

  @Override
  public Completable connect() {
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              login();
              pingPongDisconnectIfConnected();
              pingPongSubscription =
                  pingPongSrc.subscribe(o -> this.sendMessage("{\"op\":\"ping\"}"));
              completable.onComplete();
            });
  }


  private void login() {
    String key = spec.getApiKey();
    long expires = Instant.now().toEpochMilli() + 10000;
    String _val = "GET/realtime" + expires;
    try {
      Mac mac = Mac.getInstance(BaseParamsDigest.HMAC_SHA_256);
      final SecretKey secretKey =
          new SecretKeySpec(
              spec.getSecretKey().getBytes(StandardCharsets.UTF_8), BaseParamsDigest.HMAC_SHA_256);
      mac.init(secretKey);
      String signature = bytesToHex(mac.doFinal(_val.getBytes(StandardCharsets.UTF_8)));
      List<String> args =
          Stream.of(key, String.valueOf(expires), signature).collect(Collectors.toList());
      String message = objectMapper.writeValueAsString(new BybitSubscribeMessage("auth", args));
      this.sendMessage(message);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new ExchangeException("Invalid API secret", e);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    if (message.has("topic")) {
      return message.get("topic").asText();
    }
    return "";
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    LOG.info("getSubscribeMessage {}", channelName);
    return objectMapper.writeValueAsString(
        new BybitSubscribeMessage("subscribe", Collections.singletonList(channelName)));
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    LOG.info("getUnsubscribeMessage {}", channelName);
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
        case "subscribe":
        case "unsubscribe": {
          break;
        }
        case "auth": {
          isAuthorized = true;
          resubscribeChannelsAfterLogin();
          break;
        }
      }
      return;
    } else {
      switch (op) {
        // different op result of public channels and private channels
        // https://bybit-exchange.github.io/docs/v5/ws/connect#how-to-send-the-heartbeat-packet
        case "pong": {
          LOG.debug("Received PONG message: {}", message);
          return;
        }
        case "auth": {
          LOG.error("Received AUTH message: {}", jsonNode.get("ret_msg"));
          return;
        }
      }
    }
    handleMessage(jsonNode);
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
  }

  public void pingPongDisconnectIfConnected() {
    if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
      pingPongSubscription.dispose();
    }
  }

  /**
   * Custom client handler in order to execute an external, user-provided handler on channel events.
   */
  class BybitUserDataWebSocketClientHandler extends NettyWebSocketClientHandler {

    public BybitUserDataWebSocketClientHandler(
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
  @Override
  public void resubscribeChannels() {
    // need to authorize first
  }

  private void resubscribeChannelsAfterLogin() {
    for (Entry<String, Subscription> entry : channels.entrySet()) {
      try {
        Subscription subscription = entry.getValue();
        sendMessage(getSubscribeMessage(subscription.getChannelName(), subscription.getArgs()));
      } catch (IOException e) {
        LOG.error("Failed to reconnect channel: {}", entry.getKey());
      }
    }
  }

}

