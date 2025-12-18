package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.okex.dto.OkexLoginMessage;
import info.bitrich.xchangestream.okex.dto.OkexSubscribeMessage;
import info.bitrich.xchangestream.okex.dto.OkexSubscriptionTopic;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.OkexInstType;
import org.knowm.xchange.okex.dto.trade.OkexAmendOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexCancelOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexTradeParams.OkexCancelOrderParams;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static info.bitrich.xchangestream.okex.OkexStreamingService.SUBSCRIBE;
import static info.bitrich.xchangestream.okex.OkexStreamingService.UNSUBSCRIBE;

public class OkexPrivateStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(OkexPrivateStreamingService.class);

  public static final String USER_ORDER_CHANGES = "orders";
  public static final String USER_POSITION_CHANGES = "positions";
  public static final String PLACE_ORDER = "order";
  public static final String CHANGE_ORDER = "amend-order";
  public static final String CANCEL_ORDER = "cancel-order";
  private static final String LOGIN_SIGN_METHOD = "GET";
  private static final String LOGIN_SIGN_REQUEST_PATH = "/users/self/verify";
  @Getter private volatile boolean loginDone = false;
  private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);
  private Disposable pingPongSubscription;
  private final ExchangeSpecification exchangeSpecification;
  private volatile boolean needToResubscribeChannels = false;
  private final OkexExchange okexExchange;

  public OkexPrivateStreamingService(
      String privateApiUrl,
      ExchangeSpecification exchangeSpecification,
      OkexExchange okexExchange) {
    super(privateApiUrl);
    this.exchangeSpecification = exchangeSpecification;
    this.okexExchange = okexExchange;
  }

  @Override
  public Completable connect() {
    loginDone = exchangeSpecification.getApiKey() == null;
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              try {
                login();
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

  public void login() throws JsonProcessingException {
    Mac mac;
    try {
      mac = Mac.getInstance(BaseParamsDigest.HMAC_SHA_256);
      final SecretKey secretKey =
          new SecretKeySpec(
              exchangeSpecification.getSecretKey().getBytes(StandardCharsets.UTF_8),
              BaseParamsDigest.HMAC_SHA_256);
      mac.init(secretKey);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new ExchangeException("Invalid API secret", e);
    }
    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
    String toSign = timestamp + LOGIN_SIGN_METHOD + LOGIN_SIGN_REQUEST_PATH;
    String sign =
        Base64.getEncoder().encodeToString(mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8)));

    OkexLoginMessage message = new OkexLoginMessage();
    String passphrase =
        exchangeSpecification.getExchangeSpecificParametersItem("passphrase").toString();
    OkexLoginMessage.LoginArg loginArg =
        new OkexLoginMessage.LoginArg(
            exchangeSpecification.getApiKey(), passphrase, timestamp, sign);
    message.getArgs().add(loginArg);
    this.sendMessage(objectMapper.writeValueAsString(message));
  }

  public void pingPongDisconnectIfConnected() {
    if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
      pingPongSubscription.dispose();
    }
  }

  private OkexSubscriptionTopic getTopic(String channelName) {
    if (channelName.contains(USER_ORDER_CHANGES)) {
      return new OkexSubscriptionTopic(
          USER_ORDER_CHANGES, OkexInstType.ANY, null, channelName.replace(USER_ORDER_CHANGES, ""));
    } else {
      if ((channelName.contains(USER_POSITION_CHANGES))) {
        return new OkexSubscriptionTopic(
            USER_POSITION_CHANGES,
            OkexInstType.ANY,
            null,
            channelName.replace(USER_POSITION_CHANGES, ""));
      } else {
        return null;
      }
    }
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    return channelName;
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
          LOG.debug("Received pong message: {}", message);
        return;
      }
      LOG.error("Error parsing incoming message to JSON: {}", message);
      return;
    }
    // Retry after a successful login
    if (jsonNode.has("event")) {
      String event = jsonNode.get("event").asText();
      if ("login".equals(event)) {
        loginDone = true;
        if (needToResubscribeChannels) {
          this.resubscribeChannels();
          needToResubscribeChannels = false;
        }
        return;
      }
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
    if (message.has("id")) {
      return message.get("id").asText();
    } else {
      if (message.has("arg")) {
        if (message.get("arg").has("channel") && message.get("arg").has("instId")) {
          channelName =
              message.get("arg").get("channel").asText()
                  + message.get("arg").get("instId").asText();
        }
      }
    }
    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    if (args != null && args.length > 0) {
      String method = args[0].toString();
      switch (method) {
        case PLACE_ORDER:
          {
            OkexOrderRequest orderPayload;
            if (args[1] instanceof LimitOrder) {
              LimitOrder limitOrder = (LimitOrder) args[1];
              orderPayload =
                  OkexAdapters.adaptOrder(
                      limitOrder, okexExchange.getExchangeMetaData(), okexExchange.accountLevel);
            } else {
              MarketOrder marketOrder = (MarketOrder) args[1];
              orderPayload =
                  OkexAdapters.adaptOrder(
                      marketOrder, okexExchange.getExchangeMetaData(), okexExchange.accountLevel);
            }
            OkexSubscribeMessage<OkexOrderRequest> payload =
                new OkexSubscribeMessage<>(
                    channelName, PLACE_ORDER, Collections.singletonList(orderPayload));
            return objectMapper.writeValueAsString(payload);
          }
        case CHANGE_ORDER:
          {
            LimitOrder limitOrder = (LimitOrder) args[1];
            OkexAmendOrderRequest orderChangePayload =
                OkexAdapters.adaptAmendOrder(limitOrder, okexExchange.getExchangeMetaData());
            OkexSubscribeMessage<OkexAmendOrderRequest> payload =
                new OkexSubscribeMessage<>(
                    channelName, CHANGE_ORDER, Collections.singletonList(orderChangePayload));
            return objectMapper.writeValueAsString(payload);
          }
        case CANCEL_ORDER:
          {
            OkexCancelOrderParams params = (OkexCancelOrderParams) args[1];
            OkexCancelOrderRequest orderChangePayload =
                OkexCancelOrderRequest.builder()
                    .instrumentId(OkexAdapters.adaptInstrument(params.instrument))
                    .orderId(params.orderId)
                    .clientOrderId(params.getUserReference())
                    .build();
            OkexSubscribeMessage<OkexCancelOrderRequest> payload =
                new OkexSubscribeMessage<>(
                    channelName, CANCEL_ORDER, Collections.singletonList(orderChangePayload));
            return objectMapper.writeValueAsString(payload);
          }
      }
    }
    return objectMapper.writeValueAsString(
        new OkexSubscribeMessage<>(
            "", SUBSCRIBE, Collections.singletonList(getTopic(channelName))));
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    OkexSubscriptionTopic subscriptionTopic = getTopic(channelName);
    if (subscriptionTopic != null) {
      return objectMapper.writeValueAsString(
          new OkexSubscribeMessage<>(
              "", UNSUBSCRIBE, Collections.singletonList(subscriptionTopic)));
    }
    return null;
  }

  @Override
  public void resubscribeChannels() {
    needToResubscribeChannels = true;
    if (loginDone) {
      super.resubscribeChannels();
    }
  }
}
