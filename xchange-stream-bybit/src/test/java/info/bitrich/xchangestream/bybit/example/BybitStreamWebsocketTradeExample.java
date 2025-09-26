package info.bitrich.xchangestream.bybit.example;

import static info.bitrich.xchangestream.bybit.Utils.getMinAmount;
import static info.bitrich.xchangestream.bybit.example.BaseBybitExchange.connectMainApi;

import info.bitrich.xchangestream.bybit.BybitStreamingExchange;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderParams;
import org.knowm.xchange.bybit.dto.trade.details.BybitHedgeMode;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.LimitOrder.Builder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamWebsocketTradeExample {

  private static final Logger LOG =
      LoggerFactory.getLogger(BybitStreamWebsocketTradeExample.class);
  static Instrument instrument = new FuturesContract("XRP/USDT/PERP");
  static BybitStreamingExchange exchange;

  public static void main(String[] args) throws IOException {
    exchange = (BybitStreamingExchange) connectMainApi(BybitCategory.LINEAR, true);
    try {
      while (!exchange.isAlive()) {
        TimeUnit.MILLISECONDS.sleep(100);
      }
      // main(not demo) api only
      websocketTradeExample();
      websocketBatchTradeExample();
      exchange.disconnect().blockingAwait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void websocketBatchTradeExample() throws IOException, InterruptedException {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    // switch mode to two-way
    ((BybitAccountService) exchange.getAccountService()).switchPositionMode(BybitCategory.LINEAR, instrument, "USDT", 3);
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument).getMinimumAmount();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    minAmount = getMinAmount(new BigDecimal("5"), minAmount, ticker, exchange.getExchangeMetaData().getInstruments().get(instrument).getVolumeScale());
    String limitOrder1UserId = RandomStringUtils.randomAlphanumeric(20);
    LimitOrder limitOrder1 = new Builder(OrderType.ASK, instrument).originalAmount(minAmount).limitPrice(ticker.getHigh()).userReference(limitOrder1UserId).flag(BybitHedgeMode.TWOWAY).build();
    String limitOrder2UserId = RandomStringUtils.randomAlphanumeric(20);
    LimitOrder limitOrder2 = new Builder(OrderType.ASK, instrument).originalAmount(minAmount).limitPrice(ticker.getHigh().add(BigDecimal.ONE)).userReference(limitOrder2UserId).flag(BybitHedgeMode.TWOWAY).build();
    compositeDisposable.add(exchange.getStreamingTradeService().placeLimitOrder(limitOrder1)
        .subscribe(result -> {
          LOG.info("limitOrder1 is send, retCode: {}", result);
        }, throwable -> LOG.error("throwable", throwable)));
    compositeDisposable.add(exchange.getStreamingTradeService().placeLimitOrder(limitOrder2)
        .subscribe(result -> {
          LOG.info("limitOrder2 is send, retCode: {}", result);
        }, throwable -> LOG.error("throwable", throwable)));
    Thread.sleep(1000);
    LimitOrder changeOrder1 = new Builder(OrderType.ASK, instrument).originalAmount(minAmount).limitPrice(ticker.getHigh().add(new BigDecimal("0.01"))).userReference(limitOrder1UserId).flag(BybitHedgeMode.TWOWAY).build();
    LimitOrder changeOrder2 = new Builder(OrderType.ASK, instrument).originalAmount(new BigDecimal("0.01")).limitPrice(ticker.getHigh()).userReference(limitOrder2UserId).flag(BybitHedgeMode.TWOWAY).build();
    compositeDisposable.add(exchange.getStreamingTradeService().batchChangeOrder(List.of(changeOrder1,changeOrder2))
        .subscribe(result -> {
          LOG.info("changeOrder(1,2) is send, retCode: {}", result);
        }, throwable -> LOG.error("throwable", throwable)));
    Thread.sleep(1000);
    compositeDisposable.dispose();
  }

  private static void websocketTradeExample() throws IOException, InterruptedException {
    // switch mode to two-way
    ((BybitAccountService) exchange.getAccountService()).switchPositionMode(BybitCategory.LINEAR, instrument, "USDT", 3);
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument).getMinimumAmount();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    minAmount = getMinAmount(new BigDecimal("5"), minAmount, ticker, exchange.getExchangeMetaData().getInstruments().get(instrument).getVolumeScale());
    String limitOrder1UserId = RandomStringUtils.randomAlphanumeric(20);
    LimitOrder limitOrder1 = new Builder(OrderType.ASK, instrument).originalAmount(minAmount).limitPrice(ticker.getHigh()).userReference(limitOrder1UserId).flag(BybitHedgeMode.TWOWAY).build();
    Disposable limitOrder1Disposable = exchange.getStreamingTradeService().placeLimitOrder(limitOrder1)
        .subscribe(result -> {
          LOG.info("limitOrder1 is send, retCode: {}", result);
        }, throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("limitOrder1 is disposed: {}", limitOrder1Disposable.isDisposed());
    LimitOrder changeOrder1 = new Builder(OrderType.ASK, instrument).originalAmount(minAmount).limitPrice(ticker.getHigh().add(BigDecimal.ONE)).userReference(limitOrder1UserId).build();
    Disposable changeOrder1Disposable = exchange.getStreamingTradeService().changeOrder(changeOrder1)
        .subscribe(result -> {
          LOG.info("changeOrder1 is send, retCode: {}", result);
        }, throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("changeOrder1 is disposed: {}", changeOrder1Disposable.isDisposed());
    Disposable cancelOrder1Disposable = exchange.getStreamingTradeService().cancelOrder(new BybitCancelOrderParams(instrument,"",limitOrder1UserId ))
        .subscribe(result -> {
          LOG.info("cancelOrder1 is send, retCode: {}", result);
        }, throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("cancelOrder1 is disposed: {}", cancelOrder1Disposable.isDisposed());
    Thread.sleep(1000);
    MarketOrder marketOrder = new MarketOrder(OrderType.ASK, minAmount, instrument);
    marketOrder.addOrderFlag(BybitHedgeMode.TWOWAY);
    Disposable marketOrderDisposable = exchange.getStreamingTradeService().placeMarketOrder(marketOrder)
        .subscribe(result -> {
          LOG.info("marketOrder is send, retCode: {}", result);
        }, throwable -> LOG.error("throwable", throwable));
    Thread.sleep(1000);
    LOG.info("marketOrder is disposed: {}", marketOrderDisposable.isDisposed());
  }
}
