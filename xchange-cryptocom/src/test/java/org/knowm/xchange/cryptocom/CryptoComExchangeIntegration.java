package org.knowm.xchange.cryptocom;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration tests for the Crypto.com Exchange REST connector. Requires valid sandbox API keys.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class CryptoComExchangeIntegration {

  protected static final Logger logger =
      LoggerFactory.getLogger(CryptoComExchangeIntegration.class);

  protected static final String API_KEY = System.getenv("CRYPTOCOM_API_KEY");
  protected static final String SECRET_KEY = System.getenv("CRYPTOCOM_SECRET_KEY");
  protected static final CurrencyPair TEST_CURRENCY_PAIR = CurrencyPair.BTC_USDT;
  protected static final BigDecimal SMALLEST_BUY_QUANTITY = new BigDecimal("0.0001");

  protected Exchange exchange;
  protected MarketDataService marketDataService;
  protected AccountService accountService;
  protected TradeService tradeService;

  @BeforeAll
  public void setUp() {
    ExchangeSpecification exSpec = new CryptoComExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey(API_KEY);
    exSpec.setSecretKey(SECRET_KEY);
    exSpec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);
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
      exchange.remoteInit();
    } catch (IOException e) {
      logger.error("Failed to load remote metadata: {}", e.getMessage(), e);
    }
  }

  @Test
  void getAccountInfo_shouldReturnAccountInfo() throws IOException {
    AccountInfo accountInfo = accountService.getAccountInfo();
    assertThat(accountInfo).isNotNull();
    assertThat(accountInfo.getWallet()).isNotNull();
    logger.info("Account Info: {}", accountInfo);
  }

  @Test
  void getTicker_shouldReturnTickerForBtcUsdt() throws IOException {
    Ticker ticker = marketDataService.getTicker(TEST_CURRENCY_PAIR);
    assertThat(ticker).isNotNull();
    assertThat(ticker.getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
    logger.info("Ticker {}: {}", TEST_CURRENCY_PAIR, ticker);
  }

  @Test
  void getOrderBook_shouldReturnOrderBookForBtcUsdt() throws IOException {
    OrderBook orderBook = marketDataService.getOrderBook(TEST_CURRENCY_PAIR);
    assertThat(orderBook).isNotNull();
    logger.info(
        "Order Book {}: {} asks, {} bids",
        TEST_CURRENCY_PAIR,
        orderBook.getAsks().size(),
        orderBook.getBids().size());
  }

  @Test
  void getTrades_shouldReturnTradesForBtcUsdt() throws IOException {
    Trades trades = marketDataService.getTrades(TEST_CURRENCY_PAIR);
    assertThat(trades).isNotNull();
    logger.info("Trades {}: {} entries", TEST_CURRENCY_PAIR, trades.getTrades().size());
  }

  @Test
  @Disabled("Places a real (tiny) order - enable explicitly for manual execution")
  void placeMarketOrder_shouldSucceed() throws IOException {
    MarketOrder marketOrder = sampleMarketOrder();
    String orderId = tradeService.placeMarketOrder(marketOrder);
    logger.info("Placed Market Order ID: {}", orderId);
    assertThat(orderId).isNotNull().isNotEmpty();
  }

  protected MarketOrder sampleMarketOrder() {
    return new MarketOrder.Builder(Order.OrderType.BID, TEST_CURRENCY_PAIR)
        .originalAmount(SMALLEST_BUY_QUANTITY)
        .build();
  }
}
