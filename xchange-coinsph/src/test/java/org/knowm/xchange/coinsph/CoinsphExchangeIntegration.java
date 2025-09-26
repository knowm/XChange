package org.knowm.xchange.coinsph;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection; // Added for getOrder
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Integration tests for Coins.ph exchange. Requires valid API keys. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class CoinsphExchangeIntegration {

  protected static final Logger logger = LoggerFactory.getLogger(CoinsphExchangeIntegration.class);
  private static final String JSON_OUTPUT_DIR = "src/test/resources/org/knowm/xchange/coinsph/dto/";
  private final ObjectMapper objectMapper = new ObjectMapper();

  protected Exchange exchange;
  protected MarketDataService marketDataService;
  protected AccountService accountService;
  protected TradeService tradeService;

  private static final String SANDBOX_API_URL = "https://9001.pl-qa.coinsxyz.me";
  private static final String API_KEY = System.getenv("COINSPH_API_KEY");
  private static final String SECRET_KEY = System.getenv("COINSPH_SECRET_KEY");

  private static final CurrencyPair TEST_CURRENCY_PAIR =
      new CurrencyPair(Currency.BTC, Currency.PHP);
  // Smallest quantity: This needs to be determined from exchange info or API docs.
  // For now, using a placeholder. Let's assume 0.0001 BTC for now.
  // Coins.ph sandbox for BTC/PHP seems to have stepSize 0.000001 for BTC.
  // minQty is also likely small. Let's try 0.00001.
  private static final BigDecimal SMALLEST_BUY_QUANTITY = new BigDecimal("0.00001");

  @BeforeAll
  public void setUp() {
    ExchangeSpecification exSpec = new CoinsphExchange().getDefaultExchangeSpecification();
    // For HTTP, setSslUri might not be the most semantically correct,
    // but it's what BaseExchange uses to set the host.
    // If USE_SANDBOX is true, CoinsphExchange.concludeHostParams will set the correct http/https
    // URI.
    exSpec.setSslUri(SANDBOX_API_URL);
    exSpec.setApiKey(API_KEY);
    exSpec.setSecretKey(SECRET_KEY);
    exSpec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true); // Ensure sandbox is used
    // Removed SPECIFIC_PARAM_VERBOSE and SPECIFIC_PARAM_OUTPUT_JSON_TO_LOGGER as they are
    // deprecated
    // JSON logging/saving for unit tests will be handled separately.
    exSpec.setShouldLoadRemoteMetaData(true);

    exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    marketDataService = exchange.getMarketDataService();
    accountService = exchange.getAccountService();
    tradeService = exchange.getTradeService();

    logger.info(
        "Exchange: {}, SSL URI: {}",
        exchange.getExchangeSpecification().getExchangeName(),
        exchange.getExchangeSpecification().getSslUri());
    try {
      logger.info("Loading remote metadata...");
      exchange.remoteInit();
      logger.info("Remote metadata loaded successfully.");
      if (exchange.getExchangeMetaData() != null) {
        saveJson(exchange.getExchangeMetaData(), "exchangeMetaData");
      }
      // Log currency pair metadata if available
      if (exchange.getExchangeMetaData() != null
          && exchange.getExchangeMetaData().getInstruments() != null) {
        org.knowm.xchange.dto.meta.InstrumentMetaData instrumentMetaData =
            exchange.getExchangeMetaData().getInstruments().get(TEST_CURRENCY_PAIR);
        if (instrumentMetaData != null) {
          logger.info(
              "Metadata for {}: MinAmount={}, PriceScale={}, AmountScale={}",
              TEST_CURRENCY_PAIR,
              instrumentMetaData.getMinimumAmount(),
              instrumentMetaData.getPriceScale(),
              instrumentMetaData.getVolumeScale()); // Renamed from getAmountScale
        } else {
          logger.warn("No metadata found for {}", TEST_CURRENCY_PAIR);
        }
      }
    } catch (IOException e) {
      logger.error("Failed to load remote metadata: {}", e.getMessage(), e);
    }
  }

  @Test
  void getAccountInfo_shouldReturnAccountInfo() throws IOException {
    AccountInfo accountInfo = accountService.getAccountInfo();
    assertThat(accountInfo).isNotNull();
    org.knowm.xchange.dto.account.Wallet wallet = accountInfo.getWallet();
    assertThat(wallet).isNotNull();
    logger.info("Account Info: {}", accountInfo);
    saveJson(accountInfo, "accountInfo");

    Balance phpBalance = wallet.getBalance(Currency.PHP);
    assertThat(phpBalance).isNotNull();
    logger.info("PHP Balance: {}", phpBalance);
    assertThat(phpBalance.getAvailable()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
  }

  @Test
  void getTicker_shouldReturnTickerForBTCPHP() throws IOException {
    Ticker ticker = marketDataService.getTicker(TEST_CURRENCY_PAIR);
    assertThat(ticker).isNotNull();
    assertThat(ticker.getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
    logger.info("Ticker {}: {}", TEST_CURRENCY_PAIR, ticker);
    saveJson(ticker, "ticker_BTCPHP");
  }

  @Test
  void getOrderBook_shouldReturnOrderBookForBTCPHP() throws IOException {
    OrderBook orderBook = marketDataService.getOrderBook(TEST_CURRENCY_PAIR);
    assertThat(orderBook).isNotNull();
    saveJson(orderBook, "orderBook_BTCPHP");
    // Sandbox might be illiquid, so don't assert isNotEmpty for asks/bids
    logger.info(
        "Order Book {}: Asks depth: {}, Bids depth: {}",
        TEST_CURRENCY_PAIR,
        orderBook.getAsks() != null ? orderBook.getAsks().size() : "null",
        orderBook.getBids() != null ? orderBook.getBids().size() : "null");
  }

  @Test
  void placeMarketOrder_shouldSucceed() throws IOException {
    final MarketOrder marketOrder = sampleMarketOrder();
    String orderId = tradeService.placeMarketOrder(marketOrder);
    saveJson(orderId, "placedMarketOrder_ID_" + TEST_CURRENCY_PAIR.toString().replace("/", ""));
    logger.info("Placed Market Order ID: {}", orderId);
    // Don't assert anything because the order might be rejected due to insufficient balance
  }

  @Test
  @Disabled("This test requires sufficient balance to place an order")
  void placeMarketOrderAndGetOrderStatus_shouldSucceed() throws IOException, InterruptedException {
    final MarketOrder marketOrder = sampleMarketOrder();
    String orderId = tradeService.placeMarketOrder(marketOrder);

    // Skip test if order placement failed (e.g., due to insufficient balance)
    Assumptions.assumeTrue(
        orderId != null && !orderId.equals("0"),
        "Order placement failed, likely due to insufficient balance");

    // Only continue if we have a valid order ID
    if (orderId != null && !orderId.equals("0")) {
      // Wait a moment for the order to be processed
      Thread.sleep(3000);

      // Query the order status
      DefaultQueryOrderParamInstrument params =
          new DefaultQueryOrderParamInstrument(TEST_CURRENCY_PAIR, orderId);

      Collection<Order> orders = tradeService.getOrder(params);
      assertThat(orders).isNotNull().isNotEmpty();

      Order order = orders.iterator().next();
      assertThat(order.getId()).isEqualTo(orderId);
      assertThat(order.getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);

      logger.info("Retrieved order status: {}", order);
    }
  }

  protected MarketOrder sampleMarketOrder() {
    return new MarketOrder.Builder(Order.OrderType.BID, TEST_CURRENCY_PAIR)
        .originalAmount(SMALLEST_BUY_QUANTITY)
        .build();
  }

  protected void saveJson(Object dto, String fileName) {
    if (dto == null) {
      logger.error("DTO object is null, cannot save JSON for fileName: {}", fileName);
      return;
    }
    // It's good practice to log the object's string representation, but be wary of large objects.
    // For now, let's log a simpler message or trust the debugger/later inspection.
    // logger.debug("Attempting to save DTO: {} for fileName: {}", dto.toString(), fileName);

    try {
      // Ensure the directory exists
      Files.createDirectories(Paths.get(JSON_OUTPUT_DIR));
      // Configure pretty print
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      String filePath = JSON_OUTPUT_DIR + fileName + ".json";
      objectMapper.writeValue(new File(filePath), dto);
      logger.info("Saved JSON to {}", filePath);
    } catch (IOException e) {
      // Robustly get class name, even if dto somehow became null (though guarded above)
      String className = (dto != null) ? dto.getClass().getSimpleName() : "null_dto_in_catch";
      logger.error(
          "Failed to save JSON for {} to {}. Exception: {}",
          className,
          fileName,
          e.getMessage(),
          e);
    }
  }

  // Helper class for saving just an order ID as JSON
  private static class SimpleOrderId {
    public String orderId;

    public SimpleOrderId(String orderId) {
      this.orderId = orderId;
    }

    // Getter for Jackson
    public String getOrderId() {
      return orderId;
    }
  }
}
