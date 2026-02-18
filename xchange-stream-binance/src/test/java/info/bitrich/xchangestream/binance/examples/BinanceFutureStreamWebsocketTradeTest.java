package info.bitrich.xchangestream.binance.examples;

import static info.bitrich.xchangestream.binance.examples.Util.getMinAmount;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;

import info.bitrich.xchangestream.binance.BinanceStreamingTradeService;
import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.trade.BinanceCancelOrderParams;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class BinanceFutureStreamWebsocketTradeTest {

  private static final Logger LOG =
      LoggerFactory.getLogger(BinanceFutureStreamWebsocketTradeTest.class);
  private static StreamingExchange exchange;
  BinanceFutureStreamingExchange binanceFutureStreamingExchange;
  private static final Instrument instrument = new FuturesContract("ETH/USDT/PERP");
  private static final Instrument instrument2 = new FuturesContract("SOL/USDT/PERP");
  private final boolean logOutput = false;

  @Before
  public void setUp() {
    ExchangeSpecification spec = new ExchangeSpecification(BinanceFutureStreamingExchange.class);
    //  futures websocket trade not work on test net, main net only
    AuthUtils.setApiAndSecretKey(spec, "binance-main-ed25519"); // apikey and ed2519 private key
    spec.setExchangeSpecificParametersItem("ed25519", true);
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    exchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
    binanceFutureStreamingExchange = (BinanceFutureStreamingExchange) exchange;
  }

  @Test
  public void websocketTrade() throws InterruptedException, IOException {
    ProductSubscription subscription =
        ProductSubscription.create()
            // workaround to connect to userDataStream
            .addUserTrades(instrument2)
            // workaround to connect to BinanceStreamingService
            .addTicker(instrument2)
            .build();
    exchange.connect(subscription).blockingAwait();
    // wait for authorization
    while (!exchange.isAlive()) {
      Thread.sleep(100L);
    }
    BinanceStreamingTradeService binanceStreamingTradeService =
        ((BinanceStreamingTradeService) exchange.getStreamingTradeService());
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument2).getMinimumAmount();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument2);
    BigDecimal minAmountUsdt = new BigDecimal("5");
    minAmount =
        getMinAmount(
            minAmountUsdt,
            minAmount,
            ticker,
            exchange.getExchangeMetaData().getInstruments().get(instrument2).getVolumeScale());
    String limitOrderUserId = RandomStringUtils.randomAlphanumeric(20);
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, instrument2)
            .originalAmount(minAmount)
            .limitPrice(ticker.getLow())
            .userReference(limitOrderUserId)
            .build();
    Disposable limitOrderDisposable =
        binanceStreamingTradeService
            .placeLimitOrder(limitOrder)
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("placeLimitOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("placeLimitOrder error", throwable));
    Thread.sleep(1000);

    LimitOrder changeOrder =
        new LimitOrder.Builder(OrderType.BID, instrument2)
            .originalAmount(minAmount)
            .limitPrice(ticker.getLow().add(BigDecimal.ONE.negate()))
            .userReference(limitOrderUserId)
            .build();
    Disposable changeOrderDisposable =
        binanceStreamingTradeService
            .changeOrder(changeOrder)
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("changeOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("changeOrder error", throwable));
    Thread.sleep(1000);
    LOG.info("changeOrder disposed: {}", changeOrderDisposable.isDisposed());

    Disposable cancelOrderDisposable =
        binanceStreamingTradeService
            .cancelOrder(new BinanceCancelOrderParams(instrument2, null, limitOrderUserId))
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("cancelOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("cancelOrder error", throwable));
    Thread.sleep(1000);

    String marketOrderUserId = RandomStringUtils.randomAlphanumeric(20);
    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.ASK, instrument2)
            .originalAmount(minAmount)
            .userReference(marketOrderUserId)
            .build();
    Disposable marketOrderDisposable =
        binanceStreamingTradeService
            .placeMarketOrder(marketOrder)
            .doOnError(error -> LOG.error("placeMarketOrder error", error))
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("placeMarketOrder result: {}", result.toString());
                  }
                });
    Thread.sleep(1000);
  }
}
