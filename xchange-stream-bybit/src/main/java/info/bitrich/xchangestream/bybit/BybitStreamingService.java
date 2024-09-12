package info.bitrich.xchangestream.bybit;

import static info.bitrich.xchangestream.bybit.BybitStreamingExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.var;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamingService extends JsonNettyStreamingService {

  private final Logger LOG = LoggerFactory.getLogger(BybitStreamingService.class);
  public final String exchange_type;
  private final Observable<Long> pingPongSrc = Observable.interval(15, 20, TimeUnit.SECONDS);
  private Disposable pingPongSubscription;
  private final ExchangeSpecification spec;
  @Getter
  private boolean isAuthorized = false;

  public BybitStreamingService(String apiUrl, ExchangeSpecification spec) {
    super(apiUrl);
    this.exchange_type = ((BybitCategory)spec.getExchangeSpecificParametersItem(EXCHANGE_TYPE)).getValue();
    this.spec = spec;
//    this.setEnableLoggingHandler(true);
  }

  @Override
  public Completable connect() {
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              if (spec.getApiKey() != null) {
                login();
              }
              pingPongDisconnectIfConnected();
              pingPongSubscription = pingPongSrc.subscribe(
                  o -> this.sendMessage("{\"op\":\"ping\"}"));
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
      List<String> args = Stream.of(key, String.valueOf(expires), signature)
          .collect(Collectors.toList());
      String message = objectMapper.writeValueAsString(new BybitSubscribeMessage("auth", args));
      this.sendMessage(message);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new ExchangeException("Invalid API secret", e);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
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
        case "auth": {
          isAuthorized = true;
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
