package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderCancelAndReplacePayload.CancelReplaceMode.STOP_ON_FAILURE;
import static info.bitrich.xchangestream.core.StreamingExchange.WS_CONNECTION_TIMEOUT;
import static info.bitrich.xchangestream.core.StreamingExchange.WS_IDLE_TIMEOUT;
import static info.bitrich.xchangestream.core.StreamingExchange.WS_RETRY_DURATION;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketLoginPayloadWithSignature;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketLoginResponse;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderAmendPayload;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderCancelAndReplacePayload;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderCancelPayload;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderResponse;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketPayload;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketPlaceOrderPayload;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.time.Duration;
import lombok.Getter;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.trade.BinanceCancelOrderParams;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.binance.service.BinanceEd25519Signer;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceUserTradeStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceUserTradeStreamingService.class);
  private final String apiKey;
  private final String privateKey;
  CompositeDisposable compositeDisposable = new CompositeDisposable();
  @Getter private boolean authorized = false;
  private String signature = "";
  private Disposable loginDisposable;

  public BinanceUserTradeStreamingService(
      String apiUrl,
      String apiKey,
      String privateKey,
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

  @Override
  public Completable disconnect() {
    compositeDisposable.dispose();
    return super.disconnect();
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    return channelName;
  }

  public void login() {
    ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    Observable<Boolean> observable =
        this.subscribeChannel(String.valueOf(System.currentTimeMillis()), "session.logon")
            .flatMap(
                node -> {
                  TypeReference<BinanceWebsocketOrderResponse<BinanceWebsocketLoginResponse>>
                      typeReference = new TypeReference<>() {};
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
                  LOG.info("Successfully authorized to BinanceUserTradeStreamingService");
                  authorized = true;
                });
  }

  public String signPayload(String payload) {
    return BinanceEd25519Signer.signBase64(
        BinanceEd25519Signer.parsePrivateKey(privateKey), payload);
  }

  @Override
  public void messageHandler(String message) {
    super.messageHandler(message);
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return null;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {

    return message.get("id").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    String method = args[0].toString();
    switch (method) {
      case "session.logon":
        { // login
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
      case "order.place":
        {
          BinanceWebsocketPlaceOrderPayload orderPayload = null;
          if (args[1] instanceof MarketOrder) {
            MarketOrder marketOrder = (MarketOrder) args[1];
            orderPayload = BinanceStreamingAdapters.adaptPlaceOrder(marketOrder);
          } else if (args[1] instanceof LimitOrder) {
            LimitOrder limitOrder = (LimitOrder) args[1];
            orderPayload = BinanceStreamingAdapters.adaptPlaceOrder(limitOrder);
          }
          assert orderPayload != null;
          BinanceWebsocketPayload<BinanceWebsocketPlaceOrderPayload> payload =
              new BinanceWebsocketPayload<>(channelName, method, orderPayload);
          return objectMapper.writeValueAsString(payload);
        }
      case "order.modify":
        {
          LimitOrder limitOrder = (LimitOrder) args[1];
          BinanceWebsocketOrderAmendPayload amendOrderPayload =
              BinanceStreamingAdapters.adaptAmendOrder(limitOrder);
          assert amendOrderPayload != null;
          BinanceWebsocketPayload<BinanceWebsocketOrderAmendPayload> payload =
              new BinanceWebsocketPayload<>(channelName, method, amendOrderPayload);
          return objectMapper.writeValueAsString(payload);
        }
      case "order.cancel":
        {
          BinanceCancelOrderParams params = (BinanceCancelOrderParams) args[1];
          Long orderId = null;
          if (params.getOrderId() != null && !params.getOrderId().isEmpty()) {
            orderId = Long.valueOf(params.getOrderId());
          }
          BinanceWebsocketOrderCancelPayload cancelOrderPayload =
              BinanceWebsocketOrderCancelPayload.builder()
                  .symbol(BinanceAdapters.toSymbol(params.getInstrument()))
                  .orderId(orderId)
                  .origClientOrderId(params.getUserReference())
                  .newClientOrderId(params.getUserReference())
                  .timestamp(System.currentTimeMillis())
                  .build();
          BinanceWebsocketPayload<BinanceWebsocketOrderCancelPayload> payload =
              new BinanceWebsocketPayload<>(channelName, method, cancelOrderPayload);
          return objectMapper.writeValueAsString(payload);
        }
      case "order.cancelReplace":
        {
          LimitOrder limitOrder = (LimitOrder) args[1];
          BinanceCancelOrderParams params =
              new BinanceCancelOrderParams(
                  limitOrder.getInstrument(), limitOrder.getId(), limitOrder.getUserReference());
          Long cancelOrderId = null;
          if (params.getOrderId() != null && !params.getOrderId().isEmpty()) {
            cancelOrderId = Long.valueOf(params.getOrderId());
          }
          TimeInForce tif =
              BinanceAdapters.getOrderFlag(limitOrder, TimeInForce.class).orElse(TimeInForce.GTC);
          BinanceWebsocketOrderCancelAndReplacePayload orderCancelAndReplacePayload =
              BinanceWebsocketOrderCancelAndReplacePayload.builder()
                  .symbol(BinanceAdapters.toSymbol(params.getInstrument()))
                  .cancelOrderId(cancelOrderId)
                  .cancelOrigClientOrderId(params.getUserReference())
                  .symbol(BinanceAdapters.toSymbol(limitOrder.getInstrument()))
                  .side(BinanceAdapters.convert(limitOrder.getType()))
                  .newClientOrderId(limitOrder.getUserReference())
                  .type(OrderType.LIMIT)
                  .price(limitOrder.getLimitPrice())
                  .quantity(limitOrder.getOriginalAmount())
                  .timeInForce(tif)
                  .cancelReplaceMode(STOP_ON_FAILURE)
                  .timestamp(System.currentTimeMillis())
                  .build();
          BinanceWebsocketPayload<BinanceWebsocketOrderCancelAndReplacePayload> payload =
              new BinanceWebsocketPayload<>(channelName, method, orderCancelAndReplacePayload);
          return objectMapper.writeValueAsString(payload);
        }
      default:
        return null;
    }
  }
}
