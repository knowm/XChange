package info.bitrich.xchangestream.coinsph;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class CoinsphStreamingExchangeIntegration {

  private static final Logger logger =
      LoggerFactory.getLogger(CoinsphStreamingExchangeIntegration.class);

  private StreamingExchange exchange;
  private StreamingMarketDataService streamingMarketDataService;
  private StreamingAccountService streamingAccountService;
  private StreamingTradeService streamingTradeService;

  // For placing orders via REST to trigger stream events
  private TradeService restTradeService;

  // Sandbox configuration
  private static final String SANDBOX_API_URL_FOR_REST =
      "https://9001.pl-qa.coinsxyz.me"; // Used by underlying REST calls
  private static final String API_KEY = System.getenv("COINSPH_API_KEY");
  private static final String SECRET_KEY = System.getenv("COINSPH_SECRET_KEY");

  private static final CurrencyPair TEST_CURRENCY_PAIR =
      new CurrencyPair(Currency.BTC, Currency.PHP);
  private static final BigDecimal SMALLEST_BUY_QUANTITY =
      new BigDecimal("0.00001"); // From CoinsphExchangeIntegration

  @BeforeAll
  public void setUp() {
    ExchangeSpecification exSpec = new ExchangeSpecification(CoinsphStreamingExchange.class);
    exSpec.setSslUri(SANDBOX_API_URL_FOR_REST); // For REST calls like listenKey
    exSpec.setApiKey(API_KEY);
    exSpec.setSecretKey(SECRET_KEY);
    exSpec.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);
    exchange = (StreamingExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);

    // Setup REST trade service for placing orders to test streaming trade/order updates
    ExchangeSpecification restSpec = new CoinsphExchange().getDefaultExchangeSpecification();
    restSpec.setSslUri(SANDBOX_API_URL_FOR_REST);
    restSpec.setApiKey(API_KEY);
    restSpec.setSecretKey(SECRET_KEY);
    restSpec.setExchangeSpecificParametersItem(org.knowm.xchange.Exchange.USE_SANDBOX, true);
    org.knowm.xchange.Exchange restExchange = ExchangeFactory.INSTANCE.createExchange(restSpec);
    restTradeService = restExchange.getTradeService();
    try {
      logger.info("Initializing REST exchange for placing orders...");
      restExchange.remoteInit(); // Load metadata for REST part
      logger.info("REST exchange initialized.");
    } catch (Exception e) {
      logger.error("Failed to initialize REST exchange: {}", e.getMessage(), e);
    }

    logger.info("Connecting to streaming exchange...");
    // Connect to the WebSocket streams. This is blocking.
    // For Coins.ph, public stream connects directly. User stream connects after listenKey is
    // obtained.
    exchange.connect().blockingAwait();
    logger.info("Connected to streaming exchange.");

    streamingMarketDataService = exchange.getStreamingMarketDataService();
    streamingAccountService = exchange.getStreamingAccountService();
    streamingTradeService = exchange.getStreamingTradeService();
  }

  @AfterAll
  public void tearDown() {
    if (exchange != null && exchange.isAlive()) {
      logger.info("Disconnecting from streaming exchange...");
      exchange.disconnect().blockingAwait();
      logger.info("Disconnected.");
    }
  }

  @Test
  void getOrderBook_BTCPHP_shouldReceiveUpdates() throws InterruptedException {
    logger.info("Testing getOrderBook for {}...", TEST_CURRENCY_PAIR);
    Disposable orderBookDisposable =
        streamingMarketDataService
            .getOrderBook(TEST_CURRENCY_PAIR)
            .subscribe(
                orderBook -> {
                  assertThat(orderBook).isNotNull();
                  // OrderBook doesn't have getInstrument(), check the first bid or ask order
                  // instead
                  if (!orderBook.getBids().isEmpty()) {
                    assertThat(orderBook.getBids().get(0).getInstrument())
                        .isEqualTo(TEST_CURRENCY_PAIR);
                  } else if (!orderBook.getAsks().isEmpty()) {
                    assertThat(orderBook.getAsks().get(0).getInstrument())
                        .isEqualTo(TEST_CURRENCY_PAIR);
                  }
                  logger.info(
                      "Received OrderBook Update: {} asks, {} bids",
                      orderBook.getAsks().size(),
                      orderBook.getBids().size());
                  // Add more assertions if needed, e.g., non-empty, timestamps
                },
                throwable -> {
                  logger.error(
                      "Error in getOrderBook stream for {}: {}",
                      TEST_CURRENCY_PAIR,
                      throwable.getMessage(),
                      throwable);
                });

    // Let the stream run for a few seconds
    TimeUnit.SECONDS.sleep(10);
    orderBookDisposable.dispose();
    logger.info("Finished getOrderBook test for {}.", TEST_CURRENCY_PAIR);
  }

  @Test
  void getTicker_BTCPHP_shouldReceiveUpdates() throws InterruptedException {
    logger.info("Testing getTicker for {}...", TEST_CURRENCY_PAIR);
    Disposable tickerDisposable =
        streamingMarketDataService
            .getTicker(TEST_CURRENCY_PAIR)
            .subscribe(
                ticker -> {
                  assertThat(ticker).isNotNull();
                  assertThat(ticker.getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
                  logger.info("Received Ticker Update: {}", ticker);
                },
                throwable -> {
                  logger.error(
                      "Error in getTicker stream for {}: {}",
                      TEST_CURRENCY_PAIR,
                      throwable.getMessage(),
                      throwable);
                });

    TimeUnit.SECONDS.sleep(10);
    tickerDisposable.dispose();
    logger.info("Finished getTicker test for {}.", TEST_CURRENCY_PAIR);
  }

  @Test
  void getTrades_BTCPHP_shouldReceiveUpdates() throws InterruptedException {
    logger.info("Testing getTrades for {}...", TEST_CURRENCY_PAIR);
    Disposable tradesDisposable =
        streamingMarketDataService
            .getTrades(TEST_CURRENCY_PAIR)
            .subscribe(
                trade -> {
                  assertThat(trade).isNotNull();
                  assertThat(trade.getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
                  logger.info("Received Trade Update: {}", trade);
                },
                throwable -> {
                  logger.error(
                      "Error in getTrades stream for {}: {}",
                      TEST_CURRENCY_PAIR,
                      throwable.getMessage(),
                      throwable);
                });

    TimeUnit.SECONDS.sleep(10);
    tradesDisposable.dispose();
    logger.info("Finished getTrades test for {}.", TEST_CURRENCY_PAIR);
  }

  @Test
  void getBalanceChanges_shouldReceiveUpdates() throws InterruptedException {
    logger.info("Testing getBalanceChanges (all currencies)...");
    // Test for any balance change initially
    Disposable balanceDisposable =
        streamingAccountService
            .getBalanceChanges(null)
            .subscribe(
                balance -> {
                  assertThat(balance).isNotNull();
                  logger.info("Received Balance Update: {}", balance);
                  // Could add more specific assertions if we know initial/expected balances
                },
                throwable -> {
                  logger.error(
                      "Error in getBalanceChanges stream: {}", throwable.getMessage(), throwable);
                });

    // Optionally, trigger a trade or deposit/withdrawal action here if sandbox supports it
    // to see specific balance changes. For now, just listen.
    logger.info("Listening for balance changes for 10 seconds...");
    TimeUnit.SECONDS.sleep(10);
    balanceDisposable.dispose();
    logger.info("Finished getBalanceChanges test.");
  }

  @Test
  void getOrderChangesAndUserTrades_afterPlacingOrder_shouldReceiveUpdates()
      throws InterruptedException, IOException {
    logger.info("Testing getOrderChanges and getUserTrades for {}...", TEST_CURRENCY_PAIR);

    // Subscribe to order changes
    Disposable orderChangesDisposable =
        streamingTradeService
            .getOrderChanges(TEST_CURRENCY_PAIR)
            .subscribe(
                order -> {
                  assertThat(order).isNotNull();
                  assertThat(order.getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
                  logger.info("Received Order Change: {}", order);
                  // Assertions on order status, ID, etc.
                },
                throwable -> {
                  logger.error(
                      "Error in getOrderChanges stream for {}: {}",
                      TEST_CURRENCY_PAIR,
                      throwable.getMessage(),
                      throwable);
                });

    // Subscribe to user trades
    Disposable userTradesDisposable =
        streamingTradeService
            .getUserTrades(TEST_CURRENCY_PAIR)
            .subscribe(
                userTrade -> {
                  assertThat(userTrade).isNotNull();
                  assertThat(userTrade.getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
                  logger.info("Received User Trade: {}", userTrade);
                  // Assertions on trade details
                },
                throwable -> {
                  logger.error(
                      "Error in getUserTrades stream for {}: {}",
                      TEST_CURRENCY_PAIR,
                      throwable.getMessage(),
                      throwable);
                });

    // Give subscriptions a moment to establish
    TimeUnit.SECONDS.sleep(2);

    // Place a market order using REST to trigger stream events
    logger.info(
        "Placing a market order for {} {} of {} via REST...",
        Order.OrderType.BID,
        SMALLEST_BUY_QUANTITY,
        TEST_CURRENCY_PAIR);
    MarketOrder marketOrder =
        new MarketOrder.Builder(Order.OrderType.BID, TEST_CURRENCY_PAIR)
            .originalAmount(SMALLEST_BUY_QUANTITY)
            .build();

    String orderId = null;
    try {
      orderId = restTradeService.placeMarketOrder(marketOrder);
      logger.info("Market order placed via REST. Order ID: {}", orderId);
      assertThat(orderId).isNotNull().isNotEmpty();
    } catch (Exception e) {
      logger.error("Failed to place market order via REST: {}", e.getMessage(), e);
      // Clean up subscriptions if order placement fails
      orderChangesDisposable.dispose();
      userTradesDisposable.dispose();
      throw e; // Re-throw to fail the test
    }

    // Let streams run to capture updates for the placed order
    logger.info("Listening for order changes and user trades for 15 seconds...");
    TimeUnit.SECONDS.sleep(15);

    orderChangesDisposable.dispose();
    userTradesDisposable.dispose();
    logger.info("Finished getOrderChanges and getUserTrades test for {}.", TEST_CURRENCY_PAIR);
  }
}
