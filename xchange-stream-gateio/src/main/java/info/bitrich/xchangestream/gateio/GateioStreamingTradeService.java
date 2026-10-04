package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.GateioUserTradeWsResponse;
import info.bitrich.xchangestream.gateio.dto.request.userTradePayload.GateioWsOrderPayload;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioSingleOrderFuturesNotification;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioSingleOrderNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioSingleUserTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder.GateioErrorLabels;
import info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder.GateioWsCancelOrder;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.github.resilience4j.rxjava3.ratelimiter.operator.RateLimiterOperator;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import jakarta.ws.rs.NotSupportedException;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.trade.GateioCancelOrderParams;
import org.knowm.xchange.gateio.dto.trade.GateioFuturesOrderRequest;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static info.bitrich.xchangestream.gateio.config.Config.*;
import static org.knowm.xchange.gateio.GateioResilience.CANCEL_ORDER;
import static org.knowm.xchange.gateio.GateioResilience.PLACE_ORDER;

public class GateioStreamingTradeService implements StreamingTradeService {
  private static final Logger LOG = LoggerFactory.getLogger(GateioStreamingTradeService.class);
  private final GateioStreamingService service;
  private final ExchangeMetaData exchangeMetaData;
  private final GateioUserTradeStreamingService userTradeStreamingService;
  private final ResilienceRegistries resilienceRegistries;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public GateioStreamingTradeService(GateioStreamingService service, ExchangeMetaData exchangeMetaData,
                                     GateioUserTradeStreamingService userTradeStreamingService, ResilienceRegistries resilienceRegistries) {
    this.service = service;
    this.exchangeMetaData = exchangeMetaData;
    this.userTradeStreamingService = userTradeStreamingService;
    this.resilienceRegistries = resilienceRegistries;
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
  public Single<Integer> placeLimitOrder(LimitOrder limitOrder, Object... args) {
    String reqId = System.currentTimeMillis() + String.valueOf(System.nanoTime() / 1000 % 1000);
    if (limitOrder.getInstrument() instanceof FuturesContract) {
      BigDecimal contractValue = exchangeMetaData.getInstruments().get(limitOrder.getInstrument()).getContractValue();
      Observable<Integer> observable = userTradeStreamingService.subscribeChannel(FUTURES_ORDER_PLACE_CHANNEL, reqId,
              limitOrder, contractValue)
          .map(node -> {
            TypeReference<GateioUserTradeWsResponse<GateioWsOrderPayload<GateioFuturesOrderRequest>>> typeReference =
                new TypeReference<>() {
                };
            return mapper.treeToValue(node, typeReference);
          })
          .publish(shared ->
              shared.take(1).flatMap(first -> {
                if (first != null && first.getHeader() != null && "200".equals(first.getHeader().getStatus())) {
                  return shared.take(1)
                      .map(second -> {
                        if (second != null && second.getHeader() != null && "200".equals(second.getHeader().getStatus())) {
                          return 0;
                        } else {
                          assert second != null;
                          LOG.info("Error placing order: {}", second.getData() != null ? second.getData().getErrs() : null);
                          String label = (second.getData() != null && second.getData().getErrs() != null)
                              ? second.getData().getErrs().getLabel()
                              : null;
                          return label != null ? GateioErrorLabels.convert(label) : -1;
                        }
                      })
                      .timeout(1, TimeUnit.SECONDS, Observable.just(-1))
                      .defaultIfEmpty(-1);
                } else {
                  assert first != null;
                  LOG.info("Error placing order: {}", first.getData() != null ? first.getData().getErrs() : null);
                  String label = (first.getData() != null && first.getData().getErrs() != null)
                      ? first.getData().getErrs().getLabel()
                      : null;
                  return Observable.just(label != null ? GateioErrorLabels.convert(label) : -1);
                }
              })
          );
      return observable.compose(RateLimiterOperator.of(resilienceRegistries.rateLimiters().rateLimiter((PLACE_ORDER)))).firstElement().toSingle();
    } else {
      throw new UnsupportedOperationException("Only future market orders are supported");
    }
  }

  /**
   * @param marketOrder !IMPORTANT FOR SPOT for amount field in marketOrder
   *                    When type is market, the meaning depends on the side:
   *                    - side: buy refers to the quote currency, e.g. USDT in BTC_USDT
   *                    - side: sell refers to the base currency, e.g. BTC in BTC_USDT
   * @param args
   * @return
   */
  @Override
  public Single<Integer> placeMarketOrder(MarketOrder marketOrder, Object... args) {
    String reqId = System.currentTimeMillis() + String.valueOf(System.nanoTime() / 1000 % 1000);
    if (marketOrder.getInstrument() instanceof FuturesContract) {
      BigDecimal contractValue = exchangeMetaData.getInstruments().get(marketOrder.getInstrument()).getContractValue();
      Observable<Integer> observable = userTradeStreamingService.subscribeChannel(FUTURES_ORDER_PLACE_CHANNEL, reqId,
              marketOrder, contractValue)
          .map(node -> {
            TypeReference<GateioUserTradeWsResponse<GateioWsOrderPayload<GateioFuturesOrderRequest>>> typeReference =
                new TypeReference<>() {
                };
            return mapper.treeToValue(node, typeReference);
          })
          .publish(shared ->
              shared.take(1).flatMap(first -> {
                if (first != null && first.getHeader() != null && "200".equals(first.getHeader().getStatus())) {
                  return shared.take(1)
                      .map(second -> {
                        if (second != null && second.getHeader() != null && "200".equals(second.getHeader().getStatus())) {
                          return 0;
                        } else {
                          assert second != null;
                          LOG.info("Error placing order: {}", second.getData() != null ? second.getData().getErrs() : null);
                          String label = (second.getData() != null && second.getData().getErrs() != null)
                              ? second.getData().getErrs().getLabel()
                              : null;
                          return label != null ? GateioErrorLabels.convert(label) : -1;
                        }
                      })
                      .timeout(1, TimeUnit.SECONDS, Observable.just(-1))
                      .defaultIfEmpty(-1);
                } else {
                  assert first != null;
                  LOG.info("Error placing order: {}", first.getData() != null ? first.getData().getErrs() : null);
                  String label = (first.getData() != null && first.getData().getErrs() != null)
                      ? first.getData().getErrs().getLabel()
                      : null;
                  return Observable.just(label != null ? GateioErrorLabels.convert(label) : -1);
                }
              })
          );
      return observable.compose(RateLimiterOperator.of(resilienceRegistries.rateLimiters().rateLimiter((PLACE_ORDER))))
          .firstElement().toSingle();
    } else {
      Observable<Integer> observable = userTradeStreamingService.subscribeChannel(SPOT_ORDER_PLACE_CHANNEL, reqId, marketOrder)
          .map(node -> {
            TypeReference<GateioUserTradeWsResponse<GateioWsOrderPayload<GateioFuturesOrderRequest>>> typeReference =
                new TypeReference<>() {
                };
            return mapper.treeToValue(node, typeReference);
          })
          .publish(shared ->
              shared.take(1).flatMap(first -> {
                if (first != null && first.getHeader() != null && "200".equals(first.getHeader().getStatus())) {
                  return shared.take(1)
                      .map(second -> {
                        if (second != null && second.getHeader() != null && "200".equals(second.getHeader().getStatus())) {
                          return 0;
                        } else {
                          assert second != null;
                          LOG.info("Error placing order: {}", second.getData() != null ? second.getData().getErrs() : null);
                          String label = (second.getData() != null && second.getData().getErrs() != null)
                              ? second.getData().getErrs().getLabel()
                              : null;
                          return label != null ? GateioErrorLabels.convert(label) : -1;
                        }
                      })
                      .timeout(1, TimeUnit.SECONDS, Observable.just(-1))
                      .defaultIfEmpty(-1);
                } else {
                  assert first != null;
                  LOG.info("Error placing order: {}", first.getData() != null ? first.getData().getErrs() : null);
                  String label = (first.getData() != null && first.getData().getErrs() != null)
                      ? first.getData().getErrs().getLabel()
                      : null;
                  return Observable.just(label != null ? GateioErrorLabels.convert(label) : -1);
                }
              })
          );
      return observable.compose(RateLimiterOperator.of(resilienceRegistries.rateLimiters().rateLimiter((PLACE_ORDER))))
          .firstElement().toSingle();
    }
  }

  @Override
  public Single<Integer> cancelOrder(CancelOrderParams orderParams, Object... args) {
    String reqId = System.currentTimeMillis() + String.valueOf(System.nanoTime() / 1000 % 1000);
    try {
      String id = "";
      Instrument instrument = null;
      if (orderParams instanceof DefaultCancelOrderByInstrumentAndIdParams params) {
        id = params.getOrderId();
        instrument = params.getInstrument();
      } else {
        if (orderParams instanceof GateioCancelOrderParams params) {
          instrument = params.getInstrument();
          if (params.getUserReference() != null) {
            id = params.getUserReference();
          } else if (params.getOrderId() != null) {
            id = params.getOrderId();
          }
        }
      }
      if (!id.isEmpty() && instrument != null) {
        String channel;
        if (instrument instanceof FuturesContract)
          channel = FUTURES_ORDER_CANCEL_CHANNEL;
        else
          channel = SPOT_ORDER_CANCEL_CHANNEL;
        Observable<Integer> observable = userTradeStreamingService.subscribeChannel(channel, reqId, instrument, id)
            .flatMap(
                node -> {
                  TypeReference<GateioUserTradeWsResponse<GateioWsOrderPayload<GateioWsCancelOrder>>> typeReference =
                      new TypeReference<>() {
                      };
                  GateioUserTradeWsResponse<GateioWsOrderPayload<GateioWsCancelOrder>> response =
                      mapper.treeToValue(node, typeReference);
                  if (response != null && response.getHeader().getStatus().equals("200")) {
                    return Observable.just(0);
                  } else {
                    assert response != null;
                    LOG.info("Error cancel order: {}", response.getData().getErrs().getLabel());
                    return Observable.just(GateioErrorLabels.convert(response.getData().getErrs().getLabel()));
                  }
                });
        return observable.compose(RateLimiterOperator.of(resilienceRegistries.rateLimiters().rateLimiter((CANCEL_ORDER))))
            .firstElement().toSingle();
      } else
        throw new NotSupportedException("id or instrument is empty");
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public Single<Integer> changeOrder(LimitOrder order, Object... args) {
    String reqId = System.currentTimeMillis() + String.valueOf(System.nanoTime() / 1000 % 1000);
    if (order.getInstrument() instanceof FuturesContract) {
      BigDecimal contractValue = exchangeMetaData.getInstruments().get(order.getInstrument()).getContractValue();
      Observable<Integer> observable = userTradeStreamingService.subscribeChannel(FUTURES_ORDER_AMEND_CHANNEL, reqId,
              order, contractValue)
          .flatMap(
              node -> {
                TypeReference<GateioUserTradeWsResponse<GateioWsOrderPayload<GateioFuturesOrderRequest>>> typeReference =
                    new TypeReference<>() {
                    };
                GateioUserTradeWsResponse<GateioWsOrderPayload<GateioFuturesOrderRequest>> response =
                    mapper.treeToValue(node, typeReference);
                if (response != null && response.getHeader().getStatus().equals("200")) {
                  return Observable.just(0);
                } else {
                  assert response != null;
                  LOG.info("Error changing order: {}", response.getData().getErrs());
                  return Observable.just(GateioErrorLabels.convert(response.getData().getErrs().getLabel()));
                }
              });
      return observable.compose(RateLimiterOperator.of(resilienceRegistries.rateLimiters().rateLimiter((PLACE_ORDER))))
          .firstElement().toSingle();
    } else {
      throw new UnsupportedOperationException("Only future market orders are supported");
    }
  }
}
