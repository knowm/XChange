package org.knowm.xchange.gemini.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWalletJSONTest;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLevel;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class GeminiAdaptersTest {

  private final static String MARKET = "Gemini";
  private final static String EXCHANGE = "exchange";
  private final static String SYMBOL = "BTCUSD";

  @Test
  public void shouldAdaptBalances() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = GeminiWalletJSONTest.class.getResourceAsStream("/v1/account/example-account-info-balance.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GeminiBalancesResponse[] response = mapper.readValue(is, GeminiBalancesResponse[].class);

    Wallet wallet = GeminiAdapters.adaptWallet(response);

    assertEquals(2, wallet.getBalances().size());
    assertEquals(new BigDecimal("105.5"), wallet.getBalance(Currency.USD).getTotal());
    assertEquals(new BigDecimal("55.5"), wallet.getBalance(Currency.USD).getAvailable());
    assertEquals(new BigDecimal("50"), wallet.getBalance(Currency.BTC).getTotal());
    assertEquals(new BigDecimal("30"), wallet.getBalance(Currency.BTC).getAvailable());
  }

  @Test
  public void testAdaptOrdersToOrdersContainer() {

    GeminiLevel[] levels = initLevels();
    GeminiAdapters.OrdersContainer container = GeminiAdapters.adaptOrders(levels, CurrencyPair.BTC_USD, OrderType.BID);

    GeminiLevel lastLevel = levels[levels.length - 1];
    assertEquals(lastLevel.getTimestamp().multiply(new BigDecimal(1000L)).longValue(), container.getTimestamp());
    assertEquals(container.getLimitOrders().size(), levels.length);

    for (int i = 0; i < levels.length; i++) {
      LimitOrder order = container.getLimitOrders().get(i);
      long expectedTimestampMillis = levels[i].getTimestamp().multiply(new BigDecimal(1000L)).longValue();

      assertEquals(levels[i].getAmount(), order.getTradableAmount());
      assertEquals(expectedTimestampMillis, order.getTimestamp().getTime());
      assertEquals(levels[i].getPrice(), order.getLimitPrice());
    }
  }

  /**
   * Create 60 {@link GeminiLevel}s. The values increase as the array index does. The timestamps increase by 1 second + 1 minute + 1 hour + 1 day in
   * order to test the correct handling of the given timestamp.
   *
   * @return The generated responses.
   */
  private GeminiLevel[] initLevels() {

    GeminiLevel[] responses = new GeminiLevel[60];

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal timestamp = new BigDecimal("1414669893.823615468").add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      BigDecimal amount = new BigDecimal(1L + i);
      responses[i] = new GeminiLevel(price, amount, timestamp);
    }

    return responses;
  }

  @Test
  public void testAdaptOrdersToOpenOrders() {

    GeminiOrderStatusResponse[] responses = initOrderStatusResponses();
    OpenOrders orders = GeminiAdapters.adaptOrders(responses);
    assertEquals(orders.getOpenOrders().size(), responses.length);

    for (int i = 0; i < responses.length; i++) {
      LimitOrder order = orders.getOpenOrders().get(i);
      long expectedTimestampMillis = responses[i].getTimestamp().multiply(new BigDecimal(1000L)).longValue();
      Order.OrderType expectedOrderType = responses[i].getSide().equalsIgnoreCase("buy") ? Order.OrderType.BID : Order.OrderType.ASK;

      assertEquals(String.valueOf(responses[i].getId()), order.getId());
      assertEquals(responses[i].getOriginalAmount(), order.getOriginalAmount());
      assertEquals(responses[i].getRemainingAmount(), order.getRemainingAmount());
      assertEquals(responses[i].getExecutedAmount(), order.getFilledAmount());
      assertEquals(GeminiAdapters.adaptCurrencyPair(SYMBOL), order.getCurrencyPair());
      assertEquals(expectedOrderType, order.getType());
      assertEquals(expectedTimestampMillis, order.getTimestamp().getTime());
      assertEquals(responses[i].getPrice(), order.getLimitPrice());
    }
  }

  /**
   * Create 60 {@link GeminiOrderStatusResponse}s. The values increase as array index does. The timestamps increase by 1 second + 1 minute + 1 hour +
   * 1 day in order to test the correct handling of the given timestamp.
   *
   * @return The generated responses.
   */
  private GeminiOrderStatusResponse[] initOrderStatusResponses() {

    GeminiOrderStatusResponse[] responses = new GeminiOrderStatusResponse[60];

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal avgExecutionPrice = price.add(new BigDecimal(0.25 * i));
      String side = i % 2 == 0 ? "buy" : "sell";
      String type = "limit";
      BigDecimal timestamp = new BigDecimal("1414658239.41373654").add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      boolean isLive = false;
      boolean isCancelled = false;
      boolean wasForced = false;
      BigDecimal originalAmount = new BigDecimal("70");
      BigDecimal remainingAmount = originalAmount.subtract(new BigDecimal(i * 1));
      BigDecimal executedAmount = originalAmount.subtract(remainingAmount);
      responses[i] = new GeminiOrderStatusResponse(i, SYMBOL, price, avgExecutionPrice, side, type, timestamp, isLive, isCancelled,
          wasForced, originalAmount, remainingAmount, executedAmount);
    }

    return responses;
  }

  @Test
  public void testAdaptTradeHistory() {

    GeminiTradeResponse[] responses = initTradeResponses();
    Trades trades = GeminiAdapters.adaptTradeHistory(responses, SYMBOL);
    assertEquals(trades.getTrades().size(), responses.length);

    for (int i = 0; i < responses.length; i++) {
      Trade trade = trades.getTrades().get(i);
      long expectedTimestampMillis = responses[i].getTimestamp().multiply(new BigDecimal(1000L)).longValue();
      Order.OrderType expectedOrderType = responses[i].getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;

      assertEquals(responses[i].getPrice(), trade.getPrice());
      assertEquals(responses[i].getAmount(), trade.getTradableAmount());
      assertEquals(GeminiAdapters.adaptCurrencyPair(SYMBOL), trade.getCurrencyPair());
      assertEquals(expectedTimestampMillis, trade.getTimestamp().getTime());
      assertEquals(expectedOrderType, trade.getType());
      assertEquals(responses[i].getTradeId(), trade.getId());
    }
  }

  /**
   * Create 60 {@link GeminiTradeResponse}s. The values increase as array index does. The timestamps increase by 1 second + 1 minute + 1 hour + 1 day
   * in order to test the correct handling of the given timestamp.
   *
   * @return The generated responses.
   */
  private GeminiTradeResponse[] initTradeResponses() {

    GeminiTradeResponse[] responses = new GeminiTradeResponse[60];
    int tradeId = 2000;
    int orderId = 1000;

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal amount = new BigDecimal(1L + i);
      BigDecimal timestamp = new BigDecimal("1414658239.41373654").add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      String type = i % 2 == 0 ? "buy" : "sell";
      String tradeIdString = String.valueOf(tradeId++);
      String orderIdString = String.valueOf(orderId++);
      BigDecimal feeAmount = new BigDecimal(0L);
      String feeCurrency = "USD";
      responses[i] = new GeminiTradeResponse(price, amount, timestamp, MARKET, type, tradeIdString, orderIdString, feeAmount, feeCurrency);
    }

    return responses;
  }
}
