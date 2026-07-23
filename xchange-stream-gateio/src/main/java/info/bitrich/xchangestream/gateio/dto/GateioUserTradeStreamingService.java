package info.bitrich.xchangestream.gateio.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.gateio.GateioStreamingAuthHelper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.request.GateioWsUserTradeRequest;
import info.bitrich.xchangestream.gateio.dto.request.userTradePayload.GateioLoginRequest;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.handler.logging.LogLevel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static info.bitrich.xchangestream.core.StreamingExchange.*;

public class GateioUserTradeStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(GateioUserTradeStreamingService.class);
  private static final String CHANNEL_LOGIN = "spot.login";

  private final GateioStreamingAuthHelper gateioStreamingAuthHelper;
  private final String apiKey;
  @Getter
  private volatile boolean loginDone = false;
  private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);
  private Disposable pingPongSubscription;
  private final ExchangeSpecification exchangeSpecification;


  public GateioUserTradeStreamingService(String privateApiUrl, String apiKey, String apiSecret, ExchangeSpecification exchangeSpecification) {
    super(
        privateApiUrl,
        65536,
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_CONNECTION_TIMEOUT),
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_RETRY_DURATION),
        (Integer) exchangeSpecification.getExchangeSpecificParametersItem(WS_IDLE_TIMEOUT));
    this.setLoggingHandlerLevel(LogLevel.TRACE);
    this.setEnableLoggingHandler(true);

    this.apiKey = apiKey;
    this.exchangeSpecification = exchangeSpecification;
    this.gateioStreamingAuthHelper = new GateioStreamingAuthHelper(apiSecret);
  }

  @Override
  public Completable connect() {
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              try {
                login();
                if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
                  pingPongSubscription.dispose();
                }
//                pingPongSubscription = pingPongSrc.subscribe(o -> this.sendMessage("ping"));
                completable.onComplete();
              } catch (Exception e) {
                completable.onError(e);
              }
            });
  }

  public void login() throws JsonProcessingException {
    if (exchangeSpecification.getApiKey() == null) {
      loginDone = false;
    }
    Instant time = Instant.now(Config.getInstance().getClock());
    GateioLoginRequest payload = GateioLoginRequest.builder()
        .reqId(String.valueOf(Instant.now().getEpochSecond()))
        .timestamp(String.valueOf(time.getEpochSecond()))
        .apiKey(exchangeSpecification.getApiKey())
        .signature(gateioStreamingAuthHelper.signUserTrade(CHANNEL_LOGIN, Event.API.getValue(),
            String.valueOf(time.getEpochSecond()), ""))
        .build();
    GateioWsUserTradeRequest request =
        GateioWsUserTradeRequest.builder()
            .channel(CHANNEL_LOGIN)
            .event(Event.API.getValue())
            .time(time.getEpochSecond())
            .payload(payload)
            .build();
    String message = objectMapper.writeValueAsString(request);
    this.sendMessage(message);
  }

  public void messageHandler(String message) {
    LOG.debug("messageHandler: {}", message);
  }

  @Override
  public String getSubscribeMessage(String uniqueChannelName, Object... args) throws IOException {
//    String generalChannelName = uniqueChannelName.split(Config.CHANNEL_NAME_DELIMITER)[0];
    return objectMapper.writeValueAsString("");
  }

  public void pingPongDisconnectIfConnected() {
    if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
      pingPongSubscription.dispose();
    }
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return "";
  }


  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return "";
  }
}
