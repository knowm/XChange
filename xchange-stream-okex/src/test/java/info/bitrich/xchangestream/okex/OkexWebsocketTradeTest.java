package info.bitrich.xchangestream.okex;

import static info.bitrich.xchangestream.okex.Utils.getMinAmount;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.trade.OkexTradeParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class OkexWebsocketTradeTest {

  private static final Logger LOG = LoggerFactory.getLogger(OkexWebsocketTradeTest.class);
  StreamingExchange exchange;
  private final boolean logOutput = false;

  @Before
  public void setUp() {
    Properties properties = new Properties();

    try {
      properties.load(this.getClass().getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // Enter your authentication details here to run private endpoint tests
    final String API_KEY =
        (properties.getProperty("apikey") == null)
            ? System.getenv("okx_apikey")
            : properties.getProperty("apikey");
    final String SECRET_KEY =
        (properties.getProperty("secret") == null)
            ? System.getenv("okx_secretkey")
            : properties.getProperty("secret");
    final String PASSPHRASE =
        (properties.getProperty("passphrase") == null)
            ? System.getenv("okx_passphrase")
            : properties.getProperty("passphrase");

    ExchangeSpecification spec = new OkexStreamingExchange().getDefaultExchangeSpecification();
    spec.setApiKey(API_KEY);
    spec.setSecretKey(SECRET_KEY);
    spec.setExchangeSpecificParametersItem(OkexExchange.PARAM_PASSPHRASE, PASSPHRASE);
    // for xchange-stream demo
    spec.setExchangeSpecificParametersItem(OkexExchange.USE_SANDBOX, true);
    // for REST demo
    spec.setExchangeSpecificParametersItem(OkexExchange.PARAM_SIMULATED, "1");
    exchange =
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(OkexStreamingExchange.class);
    exchange.applySpecification(spec);
    exchange.connect().blockingAwait();
    // OPTION - wait for login message response
    while (!exchange.isAlive()) {
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void websocketFuturesTradeTest() throws IOException, InterruptedException {
    tradeTest(new FuturesContract("SOL/USDT/SWAP"));
  }

  @Test
  public void websocketSpotTradeTest() throws IOException, InterruptedException {
    tradeTest(new CurrencyPair("SOL/USDT"));
  }

  private void tradeTest(Instrument instrument) throws IOException, InterruptedException {
    OkexStreamingTradeService tradeService =
        (OkexStreamingTradeService) exchange.getStreamingTradeService();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    BigDecimal minAmount =
        exchange.getExchangeMetaData().getInstruments().get(instrument).getMinimumAmount();
    BigDecimal amount =
        getMinAmount(
            new BigDecimal("10"),
            minAmount,
            ticker,
            exchange.getExchangeMetaData().getInstruments().get(instrument).getVolumeScale());
    String limitOrderUserId = RandomStringUtils.randomAlphanumeric(20);
    LimitOrder limitOrder =
        new LimitOrder.Builder(BID, instrument)
            .limitPrice(ticker.getLow())
            .originalAmount(amount)
            .userReference(limitOrderUserId)
            .build();
    Disposable placeLimitOrderDisposable =
        tradeService
            .placeLimitOrder(limitOrder)
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("placeLimitOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("placeLimitOrder error", throwable));
    Thread.sleep(1000);
    if (logOutput) {
      LOG.info("placeLimitOrderDisposable disposed: {}", placeLimitOrderDisposable.isDisposed());
    }
    LimitOrder limitOrderChange =
        new LimitOrder.Builder(BID, instrument)
            .limitPrice(ticker.getLow().add(BigDecimal.ONE.negate()))
            .userReference(limitOrderUserId)
            .originalAmount(amount)
            .build();
    Disposable placeLimitOrderChangeDisposable =
        tradeService
            .changeOrder(limitOrderChange)
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("changeOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("changeOrder error", throwable));
    Thread.sleep(1000);
    if (logOutput) {
      LOG.info("changeOrder disposed: {}", placeLimitOrderChangeDisposable.isDisposed());
    }
    CancelOrderParams params =
        new OkexTradeParams.OkexCancelOrderParams(instrument, null, limitOrderUserId);
    Disposable cancelOrderDisposable =
        tradeService
            .cancelOrder(params)
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("cancelOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("cancelOrder error", throwable));
    Thread.sleep(1000);
    if (logOutput) {
      LOG.info("cancelOrderDisposable disposed: {}", cancelOrderDisposable.isDisposed());
    }
    String marketOrderUserId = RandomStringUtils.randomAlphanumeric(20);
    MarketOrder marketOrder =
        new MarketOrder.Builder(BID, instrument)
            .userReference(marketOrderUserId)
            .originalAmount(amount)
            .build();
    Disposable marketOrderDisposable =
        tradeService
            .placeMarketOrder(marketOrder)
            .subscribe(
                result -> {
                  if (logOutput) {
                    LOG.info("marketOrder result: {}", result.toString());
                  }
                },
                throwable -> LOG.error("marketOrder error", throwable));
    Thread.sleep(1000);
    if (logOutput) {
      LOG.info("marketOrderDisposable disposed: {}", marketOrderDisposable.isDisposed());
    }
  }
}
