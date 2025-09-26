package info.bitrich.xchangestream.coinsph.service;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.coinsph.CoinsphStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class StreamingTradeServiceIntegration {
  private static final Logger LOG = LoggerFactory.getLogger(StreamingTradeServiceIntegration.class);

  private static final CurrencyPair CURRENCY_PAIR = CurrencyPair.BTC_PHP;
  private static final BigDecimal SMALLEST_BUY_QUANTITY = new BigDecimal("0.00001");

  private static final String SANDBOX_API_URL = "https://9001.pl-qa.coinsxyz.me";
  private static final String API_KEY = System.getenv("COINSPH_API_KEY");
  private static final String SECRET_KEY = System.getenv("COINSPH_SECRET_KEY");

  private StreamingExchange streamingExchange;
  private StreamingTradeService streamingTradeService;
  private TradeService restTradeService;

  @Before
  public void setUp() {
    ExchangeSpecification exSpec = new ExchangeSpecification(CoinsphStreamingExchange.class);
    exSpec.setSslUri(SANDBOX_API_URL);
    exSpec.setApiKey(API_KEY);
    exSpec.setSecretKey(SECRET_KEY);
    exSpec.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);

    streamingExchange = (StreamingExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
    streamingTradeService = streamingExchange.getStreamingTradeService();

    // Setup REST trade service for placing orders
    ExchangeSpecification restSpec =
        new org.knowm.xchange.coinsph.CoinsphExchange().getDefaultExchangeSpecification();
    restSpec.setSslUri(SANDBOX_API_URL);
    restSpec.setApiKey(API_KEY);
    restSpec.setSecretKey(SECRET_KEY);
    restSpec.setExchangeSpecificParametersItem(org.knowm.xchange.Exchange.USE_SANDBOX, true);
    org.knowm.xchange.Exchange restExchange = ExchangeFactory.INSTANCE.createExchange(restSpec);
    restTradeService = restExchange.getTradeService();

    // Connect to the WebSocket
    streamingExchange.connect().blockingAwait();
  }

  @After
  public void tearDown() {
    if (streamingExchange != null) {
      streamingExchange.disconnect().blockingAwait();
    }
  }

  @Test
  public void testGetOrderChanges() throws IOException, InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<Order> receivedOrder = new AtomicReference<>();

    // Subscribe to order changes
    Disposable disposable =
        streamingTradeService
            .getOrderChanges(CURRENCY_PAIR)
            .subscribe(
                order -> {
                  LOG.info("Received order change: {}", order);
                  receivedOrder.set(order);
                  latch.countDown();
                },
                throwable -> {
                  LOG.error("Error in order changes subscription", throwable);
                });

    // Place a market order to trigger order change events
    MarketOrder marketOrder =
        new MarketOrder.Builder(Order.OrderType.BID, CURRENCY_PAIR)
            .originalAmount(SMALLEST_BUY_QUANTITY)
            .build();

    String orderId = restTradeService.placeMarketOrder(marketOrder);
    LOG.info("Placed market order with ID: {}", orderId);

    // Wait for the order change event
    boolean received = latch.await(30, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(received).isTrue();
    assertThat(receivedOrder.get()).isNotNull();
    assertThat(receivedOrder.get().getInstrument()).isEqualTo(CURRENCY_PAIR);
  }

  @Test
  public void testGetUserTrades() throws IOException, InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<UserTrade> receivedTrade = new AtomicReference<>();

    // Subscribe to user trades
    Disposable disposable =
        streamingTradeService
            .getUserTrades(CURRENCY_PAIR)
            .subscribe(
                trade -> {
                  LOG.info("Received user trade: {}", trade);
                  receivedTrade.set(trade);
                  latch.countDown();
                },
                throwable -> {
                  LOG.error("Error in user trades subscription", throwable);
                });

    // Place a market order to trigger trade events
    MarketOrder marketOrder =
        new MarketOrder.Builder(Order.OrderType.BID, CURRENCY_PAIR)
            .originalAmount(SMALLEST_BUY_QUANTITY)
            .build();

    String orderId = restTradeService.placeMarketOrder(marketOrder);
    LOG.info("Placed market order with ID: {}", orderId);

    // Wait for the trade event
    boolean received = latch.await(30, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(received).isTrue();
    assertThat(receivedTrade.get()).isNotNull();
    assertThat(receivedTrade.get().getInstrument()).isEqualTo(CURRENCY_PAIR);
  }
}
