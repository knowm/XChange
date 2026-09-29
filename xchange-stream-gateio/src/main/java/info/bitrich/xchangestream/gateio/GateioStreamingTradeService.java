package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.config.IdGenerator;
import info.bitrich.xchangestream.gateio.dto.request.payload.SpotMarketOrderPayload;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioSingleOrderFuturesNotification;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioSingleOrderNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioSingleUserTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

import java.time.Instant;

public class GateioStreamingTradeService implements StreamingTradeService {

  private final GateioStreamingService service;
  private final ExchangeMetaData exchangeMetaData;

  public GateioStreamingTradeService(GateioStreamingService service, ExchangeMetaData exchangeMetaData) {
    this.service = service;
    this.exchangeMetaData = exchangeMetaData;
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_USER_TRADES_CHANNEL, currencyPair)
//        .filter(GateioSingleUserTradeNotification.class::isInstance)
        .map(GateioSingleUserTradeNotification.class::cast)
        .map(GateioStreamingAdapters::toUserTrade);
  }

  @Override
  public Observable<UserTrade> getUserTrades() {
    return getUserTrades(null);
  }

  @Override
  public Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
    if (instrument instanceof CurrencyPair) {
      return getOrderChanges((CurrencyPair) instrument, args);
    }
    if (instrument instanceof FuturesContract) {
      return service
          .subscribeChannel(Config.FUTURES_USER_ORDERS_CHANNEL, instrument)
//          .filter(GateioSingleOrderFuturesNotification.class::isInstance)
          .map(GateioSingleOrderFuturesNotification.class::cast)
          .map(m -> GateioStreamingAdapters.toOrder
              (m, exchangeMetaData.getInstruments().get(instrument).getContractValue()));
    }
    throw new IllegalArgumentException("Instrument type not supported: " + instrument.getClass());
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_USER_ORDERS_CHANNEL, currencyPair)
//        .filter(GateioSingleOrderNotification.class::isInstance)
        .map(GateioSingleOrderNotification.class::cast)
        .map(GateioStreamingAdapters::toOrder);
  }

  @Override
  public Single<Integer> placeMarketOrder(MarketOrder marketOrder, Object... args) {
    if (marketOrder.getInstrument() instanceof FuturesContract) {
//      return service.placeMarketOrder(
//          marketOrder, exchangeMetaData.getInstruments().get(marketOrder.getInstrument()).getContractValue());
    }
//    return service.placeMarketOrder(marketOrder);
    return null;
  }

  public Single<Integer> placeMarketOrder(MarketOrder marketOrder) {
//    Observable<Integer> observable = service.subscribeChannel(Config.SPOT_USER_ORDERS_CHANNEL, marketOrder);
//        .flatMap(
//            node -> {
//              TypeReference<OkexResponse<List<OkexOrderResponse>>> typeReference =
//                  new TypeReference<>() {};
//              OkexResponse<List<OkexOrderResponse>> response =
//                  mapper.treeToValue(node, typeReference);
//              if (response.getCode().equals("0")) {
//                return Observable.just(0);
//              } else {
//                return Observable.just(Integer.parseInt(response.getData().get(0).getCode()));
//              }
//            });
//    return observable
//        .firstElement()
//        .compose(RateLimiterOperator.of(getCreateOrderRateLimiter(category)))
//        .toSingle();
    return Single.fromCallable(() -> {
      Instant time = Instant.now(Config.getInstance().getClock());
      String channel;
      Object orderPayload;
      if (marketOrder.getInstrument() instanceof FuturesContract) {
        FuturesContract futuresContract = (FuturesContract) marketOrder.getInstrument();
//        if (contractValue == null) {
//          throw new IllegalArgumentException("Contract value is required for futures orders");
//        }
//        BigDecimal size = marketOrder.getOriginalAmount().divide(contractValue, RoundingMode.DOWN);
//        orderPayload = FuturesMarketOrderPayload.builder()
//            .contract(futuresContract.getBase().getCurrencyCode() + "_" + futuresContract.getCounter().getCurrencyCode())
//            .size(marketOrder.getType() == Order.OrderType.BID ? size : size.negate())
//            .price(BigDecimal.ZERO)
//            .tif("ioc")
//            .build();
        channel = Config.FUTURES_ORDER_PLACE_CHANNEL;
      } else if (marketOrder.getInstrument() instanceof CurrencyPair) {
        orderPayload = SpotMarketOrderPayload.builder()
            .currencyPair((CurrencyPair) marketOrder.getInstrument())
            .type("market")
            .account("spot")
            .side(marketOrder.getType() == Order.OrderType.BID ? "buy" : "sell")
            .amount(marketOrder.getOriginalAmount())
            .build();
        channel = Config.SPOT_ORDER_PLACE_CHANNEL;
      } else {
        throw new IllegalArgumentException("Instrument type not supported: " + marketOrder.getInstrument().getClass());
      }
      String requestId = String.valueOf(IdGenerator.getInstance().requestId());
//      GateioWsRequest request = GateioWsRequest.builder()
//          .id(IdGenerator.getInstance().requestId())
//          .channel(channel)
//          .event(Event.API)
//          .time(time)
//          .payload(Arrays.asList(requestId, orderPayload))
//          .build();
//      request.setAuthInfo(GateioWsRequest.AuthInfo.builder()
//          .method("api_key")
//          .key(apiKey)
//          .sign(gateioStreamingAuthHelper.sign(channel, Event.API.getValue(), String.valueOf(time.getEpochSecond())))
//          .build());
//      sendMessage(objectMapper.writeValueAsString(request));
      return 0;
    });
  }


}
