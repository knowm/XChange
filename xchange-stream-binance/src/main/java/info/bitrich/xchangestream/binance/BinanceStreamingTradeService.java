package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.*;
import static org.knowm.xchange.binance.BinanceResilience.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes;
import info.bitrich.xchangestream.binance.dto.account.AccountUpdateBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.trade.*;
import info.bitrich.xchangestream.binance.dto.trade.ExecutionReportBinanceUserTransaction.ExecutionType;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.github.resilience4j.rxjava3.ratelimiter.operator.RateLimiterOperator;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.Setter;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceStreamingTradeService implements StreamingTradeService {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceStreamingTradeService.class);

  private final Subject<ExecutionReportBinanceUserTransaction> executionReportsPublisher =
      PublishSubject.<ExecutionReportBinanceUserTransaction>create().toSerialized();

  private final Subject<OrderTradeUpdateBinanceWebSocketTransaction> orderTradeUpdatePublisher =
      PublishSubject.<OrderTradeUpdateBinanceWebSocketTransaction>create().toSerialized();

  private final Subject<TradeLiteBinanceWebsocketTransaction> tradeLitePublisher =
      PublishSubject.<TradeLiteBinanceWebsocketTransaction>create().toSerialized();

  private final Subject<AccountUpdateBinanceWebSocketTransaction> positionChangesPublisher =
      PublishSubject.<AccountUpdateBinanceWebSocketTransaction>create().toSerialized();

  private volatile Disposable executionReports;
  private volatile Disposable orderTradeUpdate;
  private volatile Disposable tradeLite;
  private volatile Disposable positionChanges;
  private final BinanceExchange exchange;
  private final ResilienceRegistries resilienceRegistries;
  private volatile BinanceUserDataStreamingService binanceUserDataStreamingService;
  @Setter private volatile BinanceUserTradeStreamingService binanceUserTradeStreamingService;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BinanceStreamingTradeService(
      BinanceExchange exchange,
      BinanceUserDataStreamingService binanceUserDataStreamingService,
      BinanceUserTradeStreamingService binanceUserTradeStreamingService,
      ResilienceRegistries resilienceRegistries) {
    this.resilienceRegistries = resilienceRegistries;
    this.exchange = exchange;
    this.binanceUserDataStreamingService = binanceUserDataStreamingService;
    this.binanceUserTradeStreamingService = binanceUserTradeStreamingService;
  }

  public Observable<ExecutionReportBinanceUserTransaction> getRawExecutionReports() {
    if (binanceUserDataStreamingService == null
        || !binanceUserDataStreamingService.isSocketOpen()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
    return executionReportsPublisher;
  }

  public Observable<OrderTradeUpdateBinanceWebSocketTransaction> getRawOrderTradeUpdate() {
    if (binanceUserDataStreamingService == null
        || !binanceUserDataStreamingService.isSocketOpen()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
    return orderTradeUpdatePublisher;
  }

  public Observable<TradeLiteBinanceWebsocketTransaction> getRawTradeLite() {
    if (binanceUserDataStreamingService == null
        || !binanceUserDataStreamingService.isSocketOpen()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
    return tradeLitePublisher;
  }

  public Observable<AccountUpdateBinanceWebSocketTransaction> getRawPositionChanges(
      boolean isFuture) {
    if (binanceUserDataStreamingService == null
        || !binanceUserDataStreamingService.isSocketOpen()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
    return positionChangesPublisher;
  }

  public Observable<Order> getOrderChanges(boolean isFuture) {
    if (exchange.isFuturesEnabled()) {
      return getRawOrderTradeUpdate()
          .map(orderTradeUpdate -> orderTradeUpdate.getUpdateTransaction().toOrder(isFuture));
    } else {
      return getRawExecutionReports()
          .filter(r -> !r.getExecutionType().equals(ExecutionType.REJECTED))
          .map(binanceExec -> binanceExec.toOrder(isFuture));
    }
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return getOrderChanges(false).filter(oc -> currencyPair.equals(oc.getInstrument()));
  }

  @Override
  public Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
    return getOrderChanges(instrument instanceof FuturesContract)
        .filter(oc -> instrument.equals(oc.getInstrument()));
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return getUserTrades(false).filter(t -> t.getInstrument().equals(currencyPair));
  }

  @Override
  public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
    return getUserTrades(instrument instanceof FuturesContract)
        .filter(t -> t.getInstrument().equals(instrument));
  }

  public Observable<UserTrade> getUserTrades(boolean isFuture) {
    if (exchange.isFuturesEnabled()) {
      return getRawTradeLite().map(tradeList -> tradeList.toUserTrade(isFuture));
    } else {
      return getRawExecutionReports()
          .filter(r -> r.getExecutionType().equals(ExecutionType.TRADE))
          .map(binanceExec -> binanceExec.toUserTrade(isFuture));
    }
  }

  @Override
  public Observable<OpenPosition> getPositionChanges(Instrument instrument) {
    if (exchange.isFuturesEnabled() || exchange.isPortfolioMarginEnabled()) {
      boolean isFutures = instrument instanceof FuturesContract;
      return getRawPositionChanges(isFutures)
          .map(
              position ->
                  position.getAccountUpdate().getPositions().stream()
                      .map(p -> p.toOpenPosition(isFutures))
                      .filter(f -> f.getInstrument().equals(instrument))
                      .findFirst()
                      .orElseGet(
                          () ->
                              // return zero position
                              OpenPosition.builder()
                                  .instrument(instrument)
                                  .size(BigDecimal.ZERO)
                                  .build()));
    } else {
      throw new UnsupportedOperationException("spot not supported");
    }
  }

  public Single<Integer> placeMarketOrder(MarketOrder marketOrder) {
    return placeOrder(marketOrder);
  }

  public Single<Integer> placeLimitOrder(LimitOrder limitOrder) {
    return placeOrder(limitOrder);
  }

  public Single<Integer> placeOrder(Order order) {
    if (binanceUserTradeStreamingService.isAuthorized()) {
      if (exchange.isFuturesEnabled()) {
        return placeOrderInternal(order)
            .firstOrError()
            .compose(
                RateLimiterOperator.of(
                    resilienceRegistries
                        .rateLimiters()
                        .rateLimiter(ORDERS_PER_10_SECONDS_RATE_LIMITER)))
            .compose(
                RateLimiterOperator.of(
                    resilienceRegistries
                        .rateLimiters()
                        .rateLimiter(ORDERS_PER_MINUTE_RATE_LIMITER)));
      } else {
        if (exchange.isSpotEnabled()) {
          return placeOrderInternal(order)
              .firstOrError()
              .compose(
                  RateLimiterOperator.of(
                      resilienceRegistries
                          .rateLimiters()
                          .rateLimiter(ORDERS_PER_10_SECONDS_RATE_LIMITER)))
              .compose(
                  RateLimiterOperator.of(
                      resilienceRegistries.rateLimiters().rateLimiter(ORDERS_PER_DAY_RATE_LIMITER)))
              .compose(
                  RateLimiterOperator.of(
                      resilienceRegistries
                          .rateLimiters()
                          .rateLimiter(REQUEST_WEIGHT_RATE_LIMITER)));
        } else throw new UnsupportedOperationException("Only spot and futures supported");
      }
    } else {
      throw new UnsupportedOperationException("binanceUserTradeStreamingService not authorized");
    }
  }

  private Observable<Integer> placeOrderInternal(Order order) {
    return binanceUserTradeStreamingService
        .subscribeChannel(String.valueOf(System.nanoTime()), "order.place", order)
        .flatMap(
            node -> {
              TypeReference<BinanceWebsocketOrderResponse<BinanceNewOrder>> typeReference =
                  new TypeReference<>() {};
              BinanceWebsocketOrderResponse<BinanceNewOrder> response =
                  mapper.treeToValue(node, typeReference);
              if (response.getStatus() == 200) {
                return Observable.just(0);
              } else {
                assert response.getError() != null;
                return Observable.just(response.getError().getCode());
              }
            });
  }

  public Single<Integer> changeOrder(LimitOrder limitOrder, CancelOrderParams... orderParams) {
    if (binanceUserTradeStreamingService.isAuthorized()) {
      if (exchange.isFuturesEnabled()) {
        return binanceUserTradeStreamingService
            .subscribeChannel(String.valueOf(System.nanoTime()), "order.modify", limitOrder)
            .flatMap(
                node -> {
                  TypeReference<BinanceWebsocketOrderResponse<BinanceNewOrder>> typeReference =
                      new TypeReference<>() {};
                  BinanceWebsocketOrderResponse<BinanceNewOrder> response =
                      mapper.treeToValue(node, typeReference);
                  if (response.getStatus() == 200) {
                    return Observable.just(0);
                  } else {
                    assert response.getError() != null;
                    return Observable.just(response.getError().getCode());
                  }
                })
            .firstOrError()
            .compose(
                RateLimiterOperator.of(
                    resilienceRegistries
                        .rateLimiters()
                        .rateLimiter(ORDERS_PER_10_SECONDS_RATE_LIMITER)))
            .compose(
                RateLimiterOperator.of(
                    resilienceRegistries
                        .rateLimiters()
                        .rateLimiter(ORDERS_PER_MINUTE_RATE_LIMITER)));
      } else if (exchange.isSpotEnabled()) {
        // Cancel an existing order and immediately place a new order instead of the canceled one.
        return binanceUserTradeStreamingService
            .subscribeChannel(
                String.valueOf(System.nanoTime()),
                "order.cancelReplace",
                limitOrder,
                orderParams[0])
            .flatMap(
                node -> {
                  TypeReference<
                          BinanceWebsocketOrderResponse<
                              BinanceWebsocketOrderCancelAndReplaceResponse>>
                      typeReference = new TypeReference<>() {};
                  BinanceWebsocketOrderResponse<BinanceWebsocketOrderCancelAndReplaceResponse>
                      response = mapper.treeToValue(node, typeReference);
                  if (response.getStatus() == 200) {
                    return Observable.just(0);
                  } else {
                    assert response.getError() != null;
                    return Observable.just(response.getError().getCode());
                  }
                })
            .firstOrError()
            .compose(
                RateLimiterOperator.of(
                    resilienceRegistries
                        .rateLimiters()
                        .rateLimiter(ORDERS_PER_10_SECONDS_RATE_LIMITER)))
            .compose(
                RateLimiterOperator.of(
                    resilienceRegistries.rateLimiters().rateLimiter(ORDERS_PER_DAY_RATE_LIMITER)))
            .compose(
                RateLimiterOperator.of(
                    resilienceRegistries.rateLimiters().rateLimiter(REQUEST_WEIGHT_RATE_LIMITER)));
      } else throw new UnsupportedOperationException("Only spot and futures supported");

    } else {
      throw new UnsupportedOperationException("binanceUserTradeStreamingService not authorized");
    }
  }

  public Single<Integer> cancelOrder(CancelOrderParams orderParams) {
    if (binanceUserTradeStreamingService.isAuthorized()) {
      if (exchange.isFuturesEnabled() || exchange.isSpotEnabled()) {
        Observable<Integer> observable =
            binanceUserTradeStreamingService
                .subscribeChannel(String.valueOf(System.nanoTime()), "order.cancel", orderParams)
                .flatMap(
                    node -> {
                      TypeReference<BinanceWebsocketOrderResponse<BinanceNewOrder>> typeReference =
                          new TypeReference<>() {};
                      BinanceWebsocketOrderResponse<BinanceNewOrder> response =
                          mapper.treeToValue(node, typeReference);
                      if (response.getStatus() == 200) {
                        return Observable.just(0);
                      } else {
                        assert response.getError() != null;
                        return Observable.just(response.getError().getCode());
                      }
                    });
        return observable
            .firstOrError()
            .compose(
                RateLimiterOperator.of(
                    resilienceRegistries.rateLimiters().rateLimiter(REQUEST_WEIGHT_RATE_LIMITER)));
      } else {
        throw new UnsupportedOperationException("Only futures supported");
      }
    } else {
      throw new UnsupportedOperationException("binanceUserTradeStreamingService not authorized");
    }
  }

  /** Registers subsriptions with the streaming service for the given products. */
  public void openSubscriptions() {
    if (binanceUserDataStreamingService != null) {
      executionReports =
          binanceUserDataStreamingService
              .subscribeChannel(EXECUTION_REPORT)
              .map(this::executionReport)
              .subscribe(executionReportsPublisher::onNext);
      orderTradeUpdate =
          binanceUserDataStreamingService
              .subscribeChannel(ORDER_TRADE_UPDATE)
              .map(this::orderTradeUpdate)
              .subscribe(orderTradeUpdatePublisher::onNext);
      tradeLite =
          binanceUserDataStreamingService
              .subscribeChannel(TRADE_LITE)
              .map(this::tradeLite)
              .subscribe(tradeLitePublisher::onNext);
      positionChanges =
          binanceUserDataStreamingService
              .subscribeChannel(BinanceWebSocketTypes.ACCOUNT_UPDATE)
              .map(this::positionChanges)
              .subscribe(positionChangesPublisher::onNext);

      binanceUserDataStreamingService.setEnableLoggingHandler(true);
    }
  }

  /**
   * User data subscriptions may have to persist across multiple socket connections to different
   * URLs and therefore must act in a publisher fashion so that subscribers get an uninterrupted
   * stream.
   */
  void setUserDataStreamingService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    if (executionReports != null && !executionReports.isDisposed()) {
      executionReports.dispose();
    }
    if (orderTradeUpdate != null && !orderTradeUpdate.isDisposed()) {
      orderTradeUpdate.dispose();
    }
    if (tradeLite != null && !tradeLite.isDisposed()) {
      tradeLite.dispose();
    }
    if (positionChanges != null && !positionChanges.isDisposed()) {
      positionChanges.dispose();
    }
    this.binanceUserDataStreamingService = binanceUserDataStreamingService;
    openSubscriptions();
  }

  private OrderTradeUpdateBinanceWebSocketTransaction orderTradeUpdate(JsonNode json) {
    try {
      return mapper.treeToValue(json, OrderTradeUpdateBinanceWebSocketTransaction.class);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse order trade update", e);
    }
  }

  private TradeLiteBinanceWebsocketTransaction tradeLite(JsonNode json) {
    try {
      return mapper.treeToValue(json, TradeLiteBinanceWebsocketTransaction.class);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse order trade update", e);
    }
  }

  private AccountUpdateBinanceWebSocketTransaction positionChanges(JsonNode json) {
    try {
      return mapper.treeToValue(json, AccountUpdateBinanceWebSocketTransaction.class);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse order trade update", e);
    }
  }

  private ExecutionReportBinanceUserTransaction executionReport(JsonNode json) {
    try {
      return mapper.treeToValue(json, ExecutionReportBinanceUserTransaction.class);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse execution report", e);
    }
  }
}
