package info.bitrich.xchangestream.gateio.examples;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.gateio.GateioStreamingExchange;
import info.bitrich.xchangestream.gateio.GateioStreamingMarketDataService;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gateio.dto.trade.GateioOrderFlags;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;

import java.io.IOException;
import java.math.BigDecimal;

import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.gateio.GateioExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.gateio.dto.GateioExchangeType.FUTURES;
import static org.knowm.xchange.gateio.dto.trade.GateioTimeInForce.POC;

@Slf4j
public class GateioFuturesManualExample {
  private final Instrument instrument = new FuturesContract("ETH/USDT/PERP");
  public GateioStreamingExchange exchange;
  private final boolean logOutput = true;

  @Before
  public void before() {
    init();
  }

  @Test
  @Ignore
  public void getTickerAndFunding() throws InterruptedException {
    Disposable disposable = exchange.getStreamingMarketDataService().getTicker(instrument).subscribe(
        ticker -> {
          if (logOutput) {
            log.info("ticker {}", ticker);
          }
        }, throwable -> {
          log.error("Future throwable encountered error while subscribing to pair {}", instrument);
          log.error("", throwable);
        });
    Disposable disposable1 = exchange.getStreamingMarketDataService().getFundingRate(instrument).subscribe(
        funding -> {
          if (logOutput) {
            log.info("funding changes {}", funding);
          }
        }, throwable -> {
          log.error("Future throwable encountered error while subscribing to pair {}", instrument);
          log.error("", throwable);
        });
    Thread.sleep(40000);
    disposable1.dispose();
    Thread.sleep(20000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Ignore
  public void getOrderChanges() throws InterruptedException, IOException {
    Disposable disposable = exchange.getStreamingTradeService().getOrderChanges(instrument).subscribe(
        order -> {
          if (logOutput) {
            log.info("order changes {}", order);
          }
        }, throwable -> {
          log.error("Future throwable encountered error while subscribing to pair {}", instrument);
          log.error("", throwable);
        });
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    MarketOrder order = new MarketOrder.Builder(BID, instrument).userReference("t-" + System.currentTimeMillis()).originalAmount(new BigDecimal("0.001")).build();
    String resultMarket = exchange.getTradeService().placeMarketOrder(order);
    GateioOrderFlags flag = new GateioOrderFlags(POC);
    LimitOrder limitOrder = new LimitOrder.Builder(BID, instrument).userReference("t-" + System.currentTimeMillis()).originalAmount(new BigDecimal("0.001"))
        .limitPrice(ticker.getLow()).flag(flag).build();
    String resultLimit = exchange.getTradeService().placeLimitOrder(limitOrder);
    Thread.sleep(5000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Ignore
  public void geOrderBook() throws InterruptedException {
    Disposable disposable = exchange.getStreamingMarketDataService().getOrderBook(instrument, 400).subscribe(
        orderBook -> {
          if (logOutput) {
            log.info("OB ask.size: {}, bid.size: {}", orderBook.getAsks().size(), orderBook.getBids().size());
          }
        }, throwable -> {
          log.error("Future throwable encountered error while subscribing to pair {}", instrument);
          log.error("", throwable);
        });
    Thread.sleep(20000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Ignore
  public void geOrderBookTicker() throws InterruptedException, IOException {
    Disposable disposable = ((GateioStreamingMarketDataService) exchange.getStreamingMarketDataService()).getOrderBookTicker(instrument).subscribe(
        orderBookTicker -> {
          if (logOutput) {
            log.info("OB ticker bid: {}:{}, ask: {}:{}", orderBookTicker.getBidPrice(), orderBookTicker.getBidSize(),
                orderBookTicker.getAskPrice(), orderBookTicker.getAskSize());
          }
        }, throwable -> {
          log.error("Future throwable encountered error while subscribing to pair {}", instrument);
          log.error("", throwable);
        });
    Thread.sleep(20000);
    disposable.dispose();
    Thread.sleep(1000);
  }

  @Test
  @Ignore
  public void getTrades() throws InterruptedException {
    Disposable disposable = exchange.getStreamingMarketDataService().getTrades(instrument).subscribe(
        trade -> {
          if (logOutput) {
            log.info("{}", trade);
          }
        });
    Thread.sleep(30000);
    disposable.dispose();
  }

  private void init() {
    ExchangeSpecification spec = new GateioStreamingExchange().getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    AuthUtils.setApiAndSecretKey(spec, "gateio-main");
    exchange = (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    exchange.connect().blockingAwait();
  }
}
