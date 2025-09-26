package info.bitrich.xchangestream.coinsph.service;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.coinsph.CoinsphStreamingExchange;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
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
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class StreamingAccountServiceIntegration {
  private static final Logger LOG =
      LoggerFactory.getLogger(StreamingAccountServiceIntegration.class);

  private static final CurrencyPair CURRENCY_PAIR = CurrencyPair.BTC_PHP;
  private static final BigDecimal SMALLEST_BUY_QUANTITY = new BigDecimal("0.00001");

  private static final String SANDBOX_API_URL = "https://9001.pl-qa.coinsxyz.me";
  private static final String API_KEY = System.getenv("COINSPH_API_KEY");
  private static final String SECRET_KEY = System.getenv("COINSPH_SECRET_KEY");

  private StreamingExchange streamingExchange;
  private StreamingAccountService streamingAccountService;
  private TradeService restTradeService;

  @Before
  public void setUp() {
    ExchangeSpecification exSpec = new ExchangeSpecification(CoinsphStreamingExchange.class);
    exSpec.setSslUri(SANDBOX_API_URL);
    exSpec.setApiKey(API_KEY);
    exSpec.setSecretKey(SECRET_KEY);
    exSpec.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);

    streamingExchange = (StreamingExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
    streamingAccountService = streamingExchange.getStreamingAccountService();

    // Setup REST trade service for placing orders to trigger balance changes
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
  public void testGetBalanceChanges() throws IOException, InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<Balance> receivedBalance = new AtomicReference<>();

    // Subscribe to balance changes for PHP (counter currency)
    Disposable disposable =
        streamingAccountService
            .getBalanceChanges(Currency.PHP)
            .subscribe(
                balance -> {
                  LOG.info("Received balance change: {}", balance);
                  receivedBalance.set(balance);
                  latch.countDown();
                },
                throwable -> {
                  LOG.error("Error in balance changes subscription", throwable);
                });

    // Place a market order to trigger balance changes
    MarketOrder marketOrder =
        new MarketOrder.Builder(Order.OrderType.BID, CURRENCY_PAIR)
            .originalAmount(SMALLEST_BUY_QUANTITY)
            .build();

    String orderId = restTradeService.placeMarketOrder(marketOrder);
    LOG.info("Placed market order with ID: {} to trigger balance changes", orderId);

    // Wait for the balance change event
    boolean received = latch.await(30, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(received).isTrue();
    assertThat(receivedBalance.get()).isNotNull();
    assertThat(receivedBalance.get().getCurrency()).isEqualTo(Currency.PHP);
  }

  @Test
  public void testGetBalanceChangesAllCurrencies() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<Balance> receivedBalance = new AtomicReference<>();

    // Subscribe to all balance changes - pass null for Currency to listen to all currencies
    Disposable disposable =
        streamingAccountService
            .getBalanceChanges(null)
            .subscribe(
                balance -> {
                  LOG.info("Received balance change (any currency): {}", balance);
                  receivedBalance.set(balance);
                  latch.countDown();
                },
                throwable -> {
                  LOG.error("Error in balance changes subscription", throwable);
                });

    // Wait for any balance change event (passive listening)
    LOG.info("Listening for any balance changes for 30 seconds...");
    boolean received = latch.await(30, TimeUnit.SECONDS);
    disposable.dispose();

    if (received) {
      assertThat(receivedBalance.get()).isNotNull();
      LOG.info("Received balance change for currency: {}", receivedBalance.get().getCurrency());
    } else {
      LOG.info("No balance changes received within timeout period");
    }
  }
}
