package info.bitrich.xchangestream.binance.examples;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.OpenPosition.Type;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class BinanceFutureStreamPrivateTest {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceFutureStreamPrivateTest.class);
  private static StreamingExchange exchange;
  BinanceFutureStreamingExchange binanceFutureStreamingExchange;
  private static final Instrument instrument = new FuturesContract("ETH/USDT/PERP");
  private static final Instrument instrument2 = new FuturesContract("SOL/USDT/PERP");
  private static final boolean logOutput = true;

  @Before
  public void setUp() {
    ExchangeSpecification spec = new ExchangeSpecification(BinanceFutureStreamingExchange.class);
    // The most convenient way. Can store all keys in .ssh folder
    AuthUtils.setApiAndSecretKey(spec, "binance-demo-futures");
    spec.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    exchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
    binanceFutureStreamingExchange = (BinanceFutureStreamingExchange) exchange;
  }

  @Test
  public void getOrderAndPositionChanges() throws IOException {
    try {
      ProductSubscription subscription =
          ProductSubscription.create()
              // workaround to connect to userDataStream
              .addUserTrades(instrument)
              .build();

      exchange.connect(subscription).blockingAwait();

      Disposable orderChangesDisposable =
          exchange
              .getStreamingTradeService()
              .getOrderChanges(instrument)
              .subscribe(
                  orderChanges -> {
                    if (logOutput) {
                      LOG.info("OrderChanges subscribe: {}", orderChanges);
                    }
                    assertThat(orderChanges.getInstrument().equals(instrument)).isTrue();
                    assertThat(orderChanges.getType().equals(OrderType.ASK)).isTrue();
                  });
      Disposable userTradeLiteDisposable =
          exchange
              .getStreamingTradeService()
              .getUserTrades(instrument)
              .subscribe(
                  trade -> {
                    if (logOutput) {
                      LOG.info("trade lite subscribe: {}", trade);
                    }
                    assertThat(trade.getInstrument().equals(instrument)).isTrue();
                    assertThat(trade.getType().equals(OrderType.ASK)).isTrue();
                  });
      Disposable positionChangeDisposable =
          binanceFutureStreamingExchange
              .getStreamingTradeService()
              .getPositionChanges(instrument)
              .subscribe(
                  positionChange -> {
                    if (logOutput) {
                      LOG.info("positionChange subscribe: {}", positionChange);
                    }
                    assertThat(positionChange.getInstrument().equals(instrument)).isTrue();
                    assertThat(positionChange.getType().equals(Type.SHORT)).isTrue();
                  });
      Disposable positionChange2Disposable =
          binanceFutureStreamingExchange
              .getStreamingTradeService()
              .getPositionChanges(instrument2)
              .subscribe(
                  positionChange2 -> {
                    if (logOutput) {
                      LOG.info("positionChange2 subscribe: {}", positionChange2);
                    }
                    Assert.assertEquals(BigDecimal.ZERO, positionChange2.getSize());
                  });
      Thread.sleep(3000);
      Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
      BigDecimal amount = new BigDecimal("0.01");
      // place limit order
      String orderId =
          exchange
              .getTradeService()
              .placeLimitOrder(
                  new LimitOrder.Builder(OrderType.ASK, instrument)
                      .limitPrice(ticker.getLast())
                      .originalAmount(amount)
                      .build());
      // place market order
      String marketOrderId =
          exchange
              .getTradeService()
              .placeMarketOrder(
                  new MarketOrder.Builder(OrderType.ASK, instrument)
                      .originalAmount(amount)
                      .build());

      Thread.sleep(20000);
      orderChangesDisposable.dispose();
      userTradeLiteDisposable.dispose();
      positionChangeDisposable.dispose();
      positionChange2Disposable.dispose();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    exchange.disconnect().blockingAwait();
  }

  @Test
  public void getOrderAndPositionChangesPortfolioMarginMode() throws IOException {}
}
