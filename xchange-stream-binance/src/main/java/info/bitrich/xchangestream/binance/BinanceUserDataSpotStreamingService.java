package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.core.StreamingExchange.WS_CONNECTION_TIMEOUT;
import static info.bitrich.xchangestream.core.StreamingExchange.WS_IDLE_TIMEOUT;
import static info.bitrich.xchangestream.core.StreamingExchange.WS_RETRY_DURATION;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketLoginPayloadWithSignature;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketLoginResponse;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderResponse;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketPayload;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import lombok.Getter;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.BinanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BinanceUserDataSpotStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceUserDataSpotStreamingService.class);
  private final String apiKey;
  private final String privateKey;
  CompositeDisposable compositeDisposable = new CompositeDisposable();
  Charset charSet = StandardCharsets.UTF_8;
  @Getter
  private boolean authorized = false;
  private String signature = "";
  private Disposable loginDisposable;

  public BinanceUserDataSpotStreamingService(String apiUrl, String apiKey, String privateKey,
      ExchangeSpecification exchangeSpecification) {
    super(
        apiUrl,
        65536,
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_CONNECTION_TIMEOUT),
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_RETRY_DURATION),
        (Integer) exchangeSpecification.getExchangeSpecificParametersItem(WS_IDLE_TIMEOUT));
    this.apiKey = apiKey;
    this.privateKey = privateKey;
  }

  @Override
  public Completable connect() {
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              login();
              Disposable disposable =
                  subscribeDisconnect()
                      .subscribe(
                          obj -> {
                            authorized = false;
                            signature = "";
                          });
              compositeDisposable.add(disposable);
              completable.onComplete();
            });
  }

  public void login() {
    ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    Observable<Boolean> observable =
        this.subscribeChannel(String.valueOf(System.currentTimeMillis()), "session.logon")
            .flatMap(
                node -> {
                  TypeReference<BinanceWebsocketOrderResponse<BinanceWebsocketLoginResponse>>
                      typeReference = new TypeReference<>() {
                  };
                  BinanceWebsocketOrderResponse<BinanceWebsocketLoginResponse> response =
                      mapper.treeToValue(node, typeReference);
                  if (response.getStatus() == 200) {
                    return Observable.just(true);
                  } else {
                    return Observable.error(
                        new BinanceException(
                            response.getError().getCode(), response.getError().getMsg()));
                  }
                });
    loginDisposable =
        observable
            .firstElement()
            .doOnError(error -> LOG.error("Login error", error))
            .subscribe(
                loginResult -> {
                  LOG.info("Successfully authorized to BinanceUserDataSpotStreamingService");
                  authorized = true;
                  subscribeToUserDataChannel();
                });
  }

  private void subscribeToUserDataChannel() {
    subscribeChannel(String.valueOf(System.currentTimeMillis()), "userDataStream.subscribe").subscribe(node -> {
      LOG.info("Received user data stream subscription response: {}", node);
    });
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    if (args != null && args.length > 0) {
      String method = args[0].toString();
      switch (method) {
        case "session.logon": {// login
          long timestamp = System.currentTimeMillis();
          try {
            String loginPayload = "apiKey=" + apiKey + "&timestamp=" + timestamp;
            signature = signPayload(loginPayload);
            BinanceWebsocketLoginPayloadWithSignature loginPayloadWithSignature =
                new BinanceWebsocketLoginPayloadWithSignature(apiKey, signature, timestamp);
            BinanceWebsocketPayload<BinanceWebsocketLoginPayloadWithSignature> payload =
                new BinanceWebsocketPayload<>(
                    channelName, "session.logon", loginPayloadWithSignature);
            return objectMapper.writeValueAsString(payload);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
        case "userDataStream.subscribe": {
          try {
            BinanceWebsocketPayload<Object> payload =
                new BinanceWebsocketPayload<>(
                    channelName, "userDataStream.subscribe", null);
            return objectMapper.writeValueAsString(payload);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
    return null;
  }

  public String signPayload(String payload) throws Exception {
    Security.addProvider(new BouncyCastleProvider());
    byte[] decodePrivateKey = Base64.getDecoder().decode(privateKey.getBytes(charSet));
    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodePrivateKey);
    PrivateKeyInfo instancePrivate = PrivateKeyInfo.getInstance(pkcs8EncodedKeySpec.getEncoded());
    AsymmetricKeyParameter keyPrivate = PrivateKeyFactory.createKey(instancePrivate);
    Signer signer = new Ed25519Signer();
    signer.init(true, keyPrivate);
    var payloadBytes = payload.getBytes(charSet);
    signer.update(payloadBytes, 0, payloadBytes.length);
    byte[] signature = signer.generateSignature();
    return new String(Base64.getEncoder().encode(signature));
  }

  @Override
  public Completable disconnect() {
    compositeDisposable.dispose();
    return super.disconnect();
  }

  @Override
  public void messageHandler(String message) {
    LOG.debug("Received message: {}", message);
    super.messageHandler(message);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    try {
      if (message.get("event") != null) {
        super.handleMessage(message.get("event"));
      } else {
        super.handleMessage(message);
      }
    } catch (Exception e) {
      LOG.error("Error handling message: " + message, e);
    }
  }

  public Observable<JsonNode> subscribeChannel(BinanceWebSocketTypes eventType) {

    return super.subscribeChannel(eventType.getSerializedValue());
  }


  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    if (message.get("e") != null) {
      return message.get("e").asText();
    } else if (message.get("id") != null) {
      return message.get("id").asText();
    }
    return null;
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) {
    // No op. Disconnecting from the web socket will cancel subscriptions.
    return null;
  }

}
