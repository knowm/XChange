package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.config.IdGenerator;
import info.bitrich.xchangestream.gateio.dto.Event;
import info.bitrich.xchangestream.gateio.dto.request.GateioWsUserTradeRequest;
import info.bitrich.xchangestream.gateio.dto.request.payload.EmptyPayload;
import info.bitrich.xchangestream.gateio.dto.request.userTradePayload.GateioLoginRequest;
import info.bitrich.xchangestream.gateio.dto.request.userTradePayload.GateioWsOrderPayload;
import info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder.GateioWsAmendOrderFuture;
import info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder.GateioWsCancelOrder;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.trade.GateioFuturesOrderRequest;
import org.knowm.xchange.gateio.dto.trade.GateioSpotOrderRequest;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static info.bitrich.xchangestream.core.StreamingExchange.*;
import static org.knowm.xchange.gateio.GateioAdapters.convertVolumeToContractSize;
import static org.knowm.xchange.gateio.GateioAdapters.getGateioSize;


public class GateioUserTradeStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(GateioUserTradeStreamingService.class);
  private static final String CHANNEL_SPOT_LOGIN = "spot.login";
  private static final String CHANNEL_FUTURES_LOGIN = "futures.login";
  private final GateioStreamingAuthHelper gateioStreamingAuthHelper;
  @Getter
  private volatile boolean loginDone = false;
  private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);
  private Disposable pingPongSubscription;
  private final ExchangeSpecification exchangeSpecification;
  private final boolean isFuturesEnabled;
  private final String pingChannelName;
  private final String pongChannelName;
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();


  public GateioUserTradeStreamingService(String privateApiUrl, String apiSecret,
                                         ExchangeSpecification exchangeSpecification, boolean isFuturesEnabled) {
    super(
        privateApiUrl,
        65536,
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_CONNECTION_TIMEOUT),
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_RETRY_DURATION),
        (Integer) exchangeSpecification.getExchangeSpecificParametersItem(WS_IDLE_TIMEOUT));
    this.exchangeSpecification = exchangeSpecification;
    this.gateioStreamingAuthHelper = new GateioStreamingAuthHelper(apiSecret);
    this.isFuturesEnabled = isFuturesEnabled;
    this.pingChannelName = isFuturesEnabled ? "futures.ping" : "spot.ping";
    this.pongChannelName = isFuturesEnabled ? "futures.pong" : "spot.pong";
  }

  @Override
  public String getSubscribeMessage(String uniqueChannelName, Object... args) throws IOException {
    GateioWsUserTradeRequest request = getWsRequest(uniqueChannelName, args);
    return objectMapper.writeValueAsString(request);
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    return args[0].toString();
  }

  private GateioWsUserTradeRequest getWsRequest(String channelName, Object... args) {
    // create request common part
    String reqId = args[0].toString();
    GateioWsUserTradeRequest request =
        GateioWsUserTradeRequest.builder()
            .id(IdGenerator.getInstance().requestId())
            .reqId(reqId)
            .channel(channelName)
            .event(Event.API.getValue())
            .time(Config.getInstance().getClock().millis())
            .build();
    // create channel specific payload
    Object payload;
    switch (channelName) {
      case Config.SPOT_ORDER_PLACE_CHANNEL: {
        GateioSpotOrderRequest reqParam;
        if (ArrayUtils.get(args, 1) instanceof MarketOrder)
          reqParam = GateioAdapters.toGateioSpotOrderRequest((MarketOrder) ArrayUtils.get(args, 1));
        else
          reqParam = GateioAdapters.toGateioSpotOrderRequest((LimitOrder) ArrayUtils.get(args, 1));
        payload = GateioWsOrderPayload.<GateioSpotOrderRequest>builder().reqId(reqId).reqParam(reqParam).build();
        break;
      }
      case Config.SPOT_ORDER_CANCEL_CHANNEL:
      case Config.FUTURES_ORDER_CANCEL_CHANNEL: {
        String instrument = GateioAdapters.toGateioInstrument((Instrument) ArrayUtils.get(args, 1));
        GateioWsCancelOrder reqParam = new GateioWsCancelOrder(ArrayUtils.get(args, 2).toString(), instrument, null);
        payload = GateioWsOrderPayload.<GateioWsCancelOrder>builder().reqId(reqId).reqParam(reqParam).build();
        break;
      }
      case Config.FUTURES_ORDER_AMEND_CHANNEL: {
        LimitOrder limitOrder = (LimitOrder) ArrayUtils.get(args, 1);
        String id = "";
        if (limitOrder.getUserReference() != null) {
          id = limitOrder.getUserReference();
        } else if (limitOrder.getId() != null) {
          id = limitOrder.getId();
        }
        String size = null;
        if (limitOrder.getOriginalAmount() != null) {
          BigDecimal contractSize = convertVolumeToContractSize(limitOrder.getOriginalAmount(), (BigDecimal) ArrayUtils.get(args, 2));
          size = getGateioSize(limitOrder, contractSize);
        }
        String price = limitOrder.getLimitPrice() != null ? limitOrder.getLimitPrice().toString() : null;
        GateioWsAmendOrderFuture reqParam = GateioWsAmendOrderFuture.builder().order_id(id).size(size)
            .price(price).build();
        payload = GateioWsOrderPayload.<GateioWsAmendOrderFuture>builder().reqId(reqId).reqParam(reqParam).build();
        break;
      }
      case Config.FUTURES_ORDER_PLACE_CHANNEL: {
        GateioFuturesOrderRequest reqParam;
        if (ArrayUtils.get(args, 1) instanceof MarketOrder)
          reqParam = GateioAdapters.toGateioFuturesOrder((MarketOrder) ArrayUtils.get(args, 1),
              (BigDecimal) ArrayUtils.get(args, 2));
        else
          reqParam = GateioAdapters.toGateioFuturesOrder((LimitOrder) ArrayUtils.get(args, 1),
              (BigDecimal) ArrayUtils.get(args, 2));
        payload = GateioWsOrderPayload.<GateioFuturesOrderRequest>builder().reqId(reqId).reqParam(reqParam).build();
        break;
      }
      default:
        payload = EmptyPayload.builder().build();
    }
    request.setPayload(payload);
    return request;
  }

  @Override
  public Completable connect() {
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              try {
                login();
                pingPongDisconnectIfConnected();
                pingPongSubscription =
                    pingPongSrc.subscribe(o -> {
                      sendMessage(("{\"time\":" + System.currentTimeMillis() + ",\"channel\":\"" + pingChannelName + "\"}"));
                    });
                Disposable disposable =
                    subscribeDisconnect()
                        .subscribe(
                            obj -> {
                              loginDone = false;
                            });
                compositeDisposable.add(disposable);
                completable.onComplete();
              } catch (Exception e) {
                completable.onError(e);
              }
            });
  }

  @Override
  public Completable disconnect() {
    compositeDisposable.dispose();
    return super.disconnect();
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
        .signature(gateioStreamingAuthHelper.signUserTrade(isFuturesEnabled ? CHANNEL_FUTURES_LOGIN : CHANNEL_SPOT_LOGIN, Event.API.getValue(),
            String.valueOf(time.getEpochSecond()), ""))
        .build();
    GateioWsUserTradeRequest request =
        GateioWsUserTradeRequest.builder()
            .channel(isFuturesEnabled ? CHANNEL_FUTURES_LOGIN : CHANNEL_SPOT_LOGIN)
            .event(Event.API.getValue())
            .time(Config.getInstance().getClock().millis())
            .payload(payload)
            .build();
    String message = objectMapper.writeValueAsString(request);
    this.sendMessage(message);
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    if (message.get("request_id") != null)
      return message.get("request_id").asText();
    else {
      LOG.info("request_id empty: {}", message);
      return "";
    }
  }

  public void messageHandler(String message) {
    LOG.debug("messageHandler: {}", message);
    try {
      JsonNode jsonNode = objectMapper.readTree(message);
      if (jsonNode.get("header") != null) {
        var header = jsonNode.get("header");
        String channel = header.path("channel") != null ? header.path("channel").asText() : "";
        if (channel.equals(isFuturesEnabled ? CHANNEL_FUTURES_LOGIN : CHANNEL_SPOT_LOGIN)) {
          String status = header.path("status") != null ? header.path("status").asText() : "";
          if (status.equals("200"))
            loginDone = true;
          return;
        }
      } else {
        if (jsonNode.get("channel") != null) {
          if (jsonNode.get("channel").asText().equals(pongChannelName))
            return;
        }
      }
      handleMessage(jsonNode);
    } catch (IOException e) {
      LOG.error("Error parsing incoming message to JSON: {}", message);
    }
  }

  public void pingPongDisconnectIfConnected() {
    if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
      pingPongSubscription.dispose();
    }
  }

  @Override
  public void resubscribeChannels() {

  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return null;
  }
}
