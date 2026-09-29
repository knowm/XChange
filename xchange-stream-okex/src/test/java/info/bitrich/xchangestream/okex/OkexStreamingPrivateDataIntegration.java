package info.bitrich.xchangestream.okex;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPosition.Type;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.trade.OkexOrderFlags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Disabled
class OkexStreamingPrivateDataIntegration {

  private static final Logger LOG =
      LoggerFactory.getLogger(OkexStreamingPrivateDataIntegration.class);
  StreamingExchange exchange;
  private final Instrument instrument = new FuturesContract("BTC/USDT/SWAP");

  @BeforeEach
  void setUp() {
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

  // User trades, order changes, position changes
  @Test
  void checkStreamMarketOrder() throws Exception {
    List<Disposable> disposables = new ArrayList<>();
    InstrumentMetaData instrumentMetaData =
        exchange.getExchangeMetaData().getInstruments().get(instrument);
    BigDecimal size = instrumentMetaData.getMinimumAmount();
    disposables.add(
        exchange
            .getStreamingTradeService()
            .getOrderChanges(instrument)
            .subscribe(
                orderChange -> {
                  LOG.info("Order change: {}", orderChange);
                  assertThat(orderChange.getInstrument()).isEqualTo(instrument);
                  assertThat(orderChange.getType()).isEqualTo(Order.OrderType.BID);
                  if (orderChange.getCumulativeAmount().compareTo(BigDecimal.ZERO) > 0) {
                    assertThat(orderChange.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
                    org.assertj.core.api.Assertions.assertThat(
                            orderChange.getCumulativeAmount().compareTo(size))
                        .isEqualTo(0);
                  }
                }));
    disposables.add(
        ((OkexStreamingExchange) exchange)
            .getStreamingTradeService()
            .getPositionChanges(instrument)
            .subscribe(
                positionChange -> {
                  LOG.info("Position change: {}", positionChange);
                  assertThat(positionChange.getInstrument()).isEqualTo(instrument);
                  assertThat(positionChange.getType()).isEqualTo(Type.LONG);
                  org.assertj.core.api.Assertions.assertThat(
                          positionChange.getSize().compareTo(size) >= 0)
                      .isTrue();
                }));

    TimeUnit.SECONDS.sleep(3);
    String bidOrderId =
        exchange
            .getTradeService()
            .placeMarketOrder(
                new MarketOrder.Builder(Order.OrderType.BID, instrument)
                    .originalAmount(size)
                    .build());
    TimeUnit.SECONDS.sleep(5);
    disposables.forEach(Disposable::dispose);
  }

  @Test
  void checkStreamLimitOrder() throws Exception {
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    BigDecimal price = ticker.getLast();
    InstrumentMetaData instrumentMetaData =
        exchange.getExchangeMetaData().getInstruments().get(instrument);
    BigDecimal size = instrumentMetaData.getMinimumAmount();
    List<Disposable> disposables = new ArrayList<>();
    disposables.add(
        exchange
            .getStreamingTradeService()
            .getOrderChanges(instrument)
            .subscribe(
                orderChange -> {
                  LOG.info("Order change: {}", orderChange);
                  assertThat(orderChange.getInstrument()).isEqualTo(instrument);
                  assertThat(orderChange.getType()).isEqualTo(Order.OrderType.BID);
                  if (orderChange.getCumulativeAmount().compareTo(BigDecimal.ZERO) > 0) {
                    assertThat(orderChange.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
                  }
                }));
    disposables.add(
        ((OkexStreamingExchange) exchange)
            .getStreamingTradeService()
            .getPositionChanges(instrument)
            .subscribe(
                positionChange -> {
                  LOG.info("Position change: {}", positionChange);
                  assertThat(positionChange.getInstrument()).isEqualTo(instrument);
                  assertThat(positionChange.getType()).isEqualTo(Type.LONG);
                }));

    TimeUnit.SECONDS.sleep(3);
    String bidOrderId =
        exchange
            .getTradeService()
            .placeLimitOrder(
                new LimitOrder.Builder(Order.OrderType.BID, instrument)
                    .originalAmount(size)
                    .limitPrice(price)
                    .flag(OkexOrderFlags.POST_ONLY)
                    .build());
    TimeUnit.SECONDS.sleep(5);
    disposables.forEach(Disposable::dispose);
  }
}
