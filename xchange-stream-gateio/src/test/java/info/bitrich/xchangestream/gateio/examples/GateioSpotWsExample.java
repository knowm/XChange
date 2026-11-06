package info.bitrich.xchangestream.gateio.examples;

import info.bitrich.xchangestream.gateio.GateioStreamingExchange;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gateio.dto.trade.GateioCancelOrderParams;
import org.knowm.xchange.instrument.Instrument;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import static info.bitrich.xchangestream.gateio.examples.Utils.getMinAmount;
import static org.knowm.xchange.dto.Order.OrderType.BID;

@Slf4j
public class GateioSpotWsExample {
  private final Instrument instrument = new CurrencyPair("SOL/USDT");
  public GateioStreamingExchange exchange;
  private final boolean logOutput = false;

  @BeforeEach
  public void before() {
    exchange = GateioExampleSetUp.initSpot();
  }

  @Test
  @Disabled
  public void placeMarketOrderWs() throws InterruptedException, IOException {
    while (!exchange.isAlive())
      Thread.sleep(100);
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument).getMinimumAmount();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    minAmount =
        getMinAmount(
            new BigDecimal("5"),
            minAmount,
            ticker,
            exchange.getExchangeMetaData().getInstruments().get(instrument).getVolumeScale());
    BigDecimal minUSDT = minAmount.multiply(ticker.getLast());
    if (minUSDT.compareTo(new BigDecimal("4")) < 0)
      minUSDT = new BigDecimal("4");
    String orderUserReference = "t-" + System.currentTimeMillis() + System.nanoTime() / 1000 % 1000;
    MarketOrder marketBuyOrder = new MarketOrder.Builder(BID, instrument).originalAmount(minUSDT).userReference(orderUserReference).build();
    AtomicReference<BigDecimal> orderAmountInBaseCurrency = new AtomicReference<>();
    orderAmountInBaseCurrency.set(BigDecimal.ZERO);
    Disposable orderChangeDisposable = exchange.getStreamingTradeService().getOrderChanges(instrument).subscribe(
        orderChange -> {
          log.info("orderChange: {}, ", orderChange);
          if (orderChange.getUserReference().equals(orderUserReference)) {
            orderAmountInBaseCurrency.set(orderChange.getCumulativeAmount());
          }
        }
    );
    Disposable marketBuyOrderDisposable =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(marketBuyOrder)
            .subscribe(
                result -> {
                  log.info("marketBuyOrder is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(3000);
    log.info("marketBuyOrder is disposed: {}", marketBuyOrderDisposable.isDisposed());
    if (orderAmountInBaseCurrency.get().compareTo(BigDecimal.ZERO) > 0) {
      log.info("orderAmountInBaseCurrency: {}", orderAmountInBaseCurrency.get());
      MarketOrder marketSellOrder = new MarketOrder.Builder(Order.OrderType.ASK, instrument).originalAmount(orderAmountInBaseCurrency.get())
          .build();
      Disposable marketSellOrderDisposable =
          exchange
              .getStreamingTradeService()
              .placeMarketOrder(marketSellOrder)
              .subscribe(
                  result -> {
                    log.info("marketSellOrder is send, retCode: {}", result);
                  },
                  throwable -> log.error("throwable", throwable));
      Thread.sleep(1000);
      log.info("marketSellOrder is disposed: {}", marketSellOrderDisposable.isDisposed());
    }
    String limitBuyOrderUserRef = "t-" + System.currentTimeMillis() + System.nanoTime() / 1000 % 1000;
    LimitOrder limitBuyOrder = new LimitOrder.Builder(BID, instrument).originalAmount(minAmount)
        .userReference(limitBuyOrderUserRef).limitPrice(ticker.getLow()).build();
    Disposable limitBuyOrderDisposable =
        exchange
            .getStreamingTradeService()
            .placeLimitOrder(limitBuyOrder)
            .subscribe(
                result -> {
                  log.info("limitBuyOrder is send, retCode: {}", result);
                },
                throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("limitBuyOrder is disposed: {}", limitBuyOrderDisposable.isDisposed());
    GateioCancelOrderParams cancelOrderParams1 = new GateioCancelOrderParams("", instrument, limitBuyOrderUserRef);
    Disposable cancelOrder1 = exchange.getStreamingTradeService().cancelOrder(cancelOrderParams1).subscribe(result -> {
          log.info("cancel limitBuyOrder is send, retCode: {}", result);
        },
        throwable -> log.error("throwable", throwable));
    Thread.sleep(1000);
    log.info("cancelOrder1 is disposed: {}", cancelOrder1.isDisposed());
  }
}
