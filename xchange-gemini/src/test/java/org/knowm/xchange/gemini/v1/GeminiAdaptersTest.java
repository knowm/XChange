package org.knowm.xchange.gemini.v1;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiTrailingVolumeResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWalletJSONTest;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLevel;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;
import org.knowm.xchange.instrument.Instrument;

class GeminiAdaptersTest {

  private static final String MARKET = "Gemini";
  private static final String SYMBOL = "BTCUSD";

  @Test
  void shouldAdaptBalances() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        GeminiWalletJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/account/example-account-info-balance.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GeminiBalancesResponse[] response = mapper.readValue(is, GeminiBalancesResponse[].class);

    Wallet wallet = GeminiAdapters.adaptWallet(response);

    assertThat(wallet.getBalances().size()).isEqualTo(2);
    assertThat(wallet.getBalance(Currency.USD).getTotal()).isEqualTo(new BigDecimal("105.5"));
    assertThat(wallet.getBalance(Currency.USD).getAvailable()).isEqualTo(new BigDecimal("55.5"));
    assertThat(wallet.getBalance(Currency.BTC).getTotal()).isEqualTo(new BigDecimal("50"));
    assertThat(wallet.getBalance(Currency.BTC).getAvailable()).isEqualTo(new BigDecimal("30"));
  }

  @Test
  void adaptOrderResponseToOrder() throws Exception {
    InputStream resourceAsStream =
        GeminiAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/order/example-get-order-data.json");
    GeminiOrderStatusResponse response =
        new ObjectMapper().readValue(resourceAsStream, GeminiOrderStatusResponse.class);
    Order order = GeminiAdapters.adaptOrder(response);

    assertThat(order.getId()).isEqualTo("54323412782");
    assertThat(order.getTimestamp()).isEqualTo(new Date(1629770740526L));
    assertThat(order.getInstrument()).isEqualTo(CurrencyPair.ETH_USD);

    assertThat(order.getType()).isEqualTo(OrderType.ASK);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.001"));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("0.00"));

    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.OPEN);
    assertThat(order.getCumulativeAmount()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  void adaptOrderResponseContainingTradesToOrder() throws Exception {
    InputStream resourceAsStream =
        GeminiAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/order/example-get-order-data-trades-included.json");
    GeminiOrderStatusResponse response =
        new ObjectMapper().readValue(resourceAsStream, GeminiOrderStatusResponse.class);
    Order order = GeminiAdapters.adaptOrder(response);

    assertThat(order.getId()).isEqualTo("54516439535");
    assertThat(order.getUserReference()).isEqualTo("TESTID0");

    assertThat(order.getTimestamp()).isEqualTo(new Date(1629872367328L));
    assertThat(order.getInstrument()).isEqualTo(CurrencyPair.ETH_USD);

    assertThat(order.getType()).isEqualTo(OrderType.ASK);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.001"));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("3206.00"));
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0.001"));
    assertThat(order.getRemainingAmount()).isEqualTo(new BigDecimal("0.000"));
    assertThat(order.getFee()).isEqualTo(new BigDecimal("0.003206"));

    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
  }

  @Test
  void adaptOrdersToOrdersContainer() {

    GeminiLevel[] levels = initLevels();
    GeminiAdapters.OrdersContainer container =
        GeminiAdapters.adaptOrders(levels, CurrencyPair.BTC_USD, OrderType.BID);

    GeminiLevel lastLevel = levels[levels.length - 1];
    assertThat(container.getTimestamp())
        .isEqualTo(lastLevel.getTimestamp().multiply(new BigDecimal(1000L)).longValue());
    assertThat(levels.length).isEqualTo(container.getLimitOrders().size());

    for (int i = 0; i < levels.length; i++) {
      LimitOrder order = container.getLimitOrders().get(i);
      long expectedTimestampMillis =
          levels[i].getTimestamp().multiply(new BigDecimal(1000L)).longValue();

      assertThat(order.getOriginalAmount()).isEqualTo(levels[i].getAmount());
      assertThat(order.getTimestamp().getTime()).isEqualTo(expectedTimestampMillis);
      assertThat(order.getLimitPrice()).isEqualTo(levels[i].getPrice());
    }
  }

  /**
   * Create 60 {@link GeminiLevel}s. The values increase as the array index does. The timestamps
   * increase by 1 second + 1 minute + 1 hour + 1 day in order to test the correct handling of the
   * given timestamp.
   *
   * @return The generated responses.
   */
  private GeminiLevel[] initLevels() {

    GeminiLevel[] responses = new GeminiLevel[60];

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal timestamp =
          new BigDecimal("1414669893.823615468")
              .add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      BigDecimal amount = new BigDecimal(1L + i);
      responses[i] = new GeminiLevel(price, amount, timestamp);
    }

    return responses;
  }

  @Test
  void adaptOrdersToOpenOrders() {

    GeminiOrderStatusResponse[] responses = initOrderStatusResponses(SYMBOL);
    OpenOrders orders = GeminiAdapters.adaptOrders(responses);
    assertThat(responses.length).isEqualTo(orders.getOpenOrders().size());

    for (int i = 0; i < responses.length; i++) {
      LimitOrder order = orders.getOpenOrders().get(i);
      long expectedTimestampMillis =
          new BigDecimal(responses[i].getTimestamp()).multiply(new BigDecimal(1000L)).longValue();
      Order.OrderType expectedOrderType =
          responses[i].getSide().equalsIgnoreCase("buy")
              ? Order.OrderType.BID
              : Order.OrderType.ASK;

      assertThat(order.getId()).isEqualTo(String.valueOf(responses[i].getId()));
      assertThat(order.getOriginalAmount()).isEqualTo(responses[i].getOriginalAmount());
      assertThat(order.getRemainingAmount()).isEqualTo(responses[i].getRemainingAmount());
      assertThat(order.getOriginalAmount().subtract(order.getRemainingAmount()))
          .isEqualTo(responses[i].getExecutedAmount());
      assertThat(order.getInstrument()).isEqualTo(GeminiAdapters.adaptCurrencyPair(SYMBOL));
      assertThat(order.getType()).isEqualTo(expectedOrderType);
      assertThat(order.getTimestamp().getTime()).isEqualTo(expectedTimestampMillis);
      assertThat(order.getLimitPrice()).isEqualTo(responses[i].getPrice());
    }
  }

  @Test
  void adaptOrdersToOpenOrdersFiltersByCurrencyPair() {

    GeminiOrderStatusResponse[] responsesToRetain =
        ArrayUtils.addAll(initOrderStatusResponses(SYMBOL), initOrderStatusResponses(SYMBOL));

    GeminiOrderStatusResponse[] responses =
        ArrayUtils.addAll(initOrderStatusResponses("ETHBTC"), responsesToRetain);

    OpenOrders orders = GeminiAdapters.adaptOrders(responses, CurrencyPair.BTC_USD);
    assertThat(responsesToRetain.length).isEqualTo(orders.getOpenOrders().size());

    for (int i = 0; i < responsesToRetain.length; i++) {
      LimitOrder order = orders.getOpenOrders().get(i);
      long expectedTimestampMillis =
          new BigDecimal(responsesToRetain[i].getTimestamp())
              .multiply(new BigDecimal(1000L))
              .longValue();
      Order.OrderType expectedOrderType =
          responsesToRetain[i].getSide().equalsIgnoreCase("buy")
              ? Order.OrderType.BID
              : Order.OrderType.ASK;

      assertThat(order.getId()).isEqualTo(String.valueOf(responsesToRetain[i].getId()));
      assertThat(order.getOriginalAmount()).isEqualTo(responsesToRetain[i].getOriginalAmount());
      assertThat(order.getRemainingAmount()).isEqualTo(responsesToRetain[i].getRemainingAmount());
      assertThat(order.getOriginalAmount().subtract(order.getRemainingAmount()))
          .isEqualTo(responsesToRetain[i].getExecutedAmount());
      assertThat(order.getInstrument()).isEqualTo(GeminiAdapters.adaptCurrencyPair(SYMBOL));
      assertThat(order.getType()).isEqualTo(expectedOrderType);
      assertThat(order.getTimestamp().getTime()).isEqualTo(expectedTimestampMillis);
      assertThat(order.getLimitPrice()).isEqualTo(responsesToRetain[i].getPrice());
    }
  }

  /**
   * Create 60 {@link GeminiOrderStatusResponse}s. The values increase as array index does. The
   * timestamps increase by 1 second + 1 minute + 1 hour + 1 day in order to test the correct
   * handling of the given timestamp.
   *
   * @return The generated responses.
   */
  private GeminiOrderStatusResponse[] initOrderStatusResponses(String symbol) {

    GeminiOrderStatusResponse[] responses = new GeminiOrderStatusResponse[60];

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal avgExecutionPrice = price.add(new BigDecimal(0.25 * i));
      String side = i % 2 == 0 ? "buy" : "sell";
      String type = "limit";
      BigDecimal timestamp =
          new BigDecimal("1414658239.41373654")
              .add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      boolean isLive = false;
      boolean isCancelled = false;
      boolean wasForced = false;
      BigDecimal originalAmount = new BigDecimal("70");
      BigDecimal remainingAmount = originalAmount.subtract(new BigDecimal(i * 1));
      BigDecimal executedAmount = originalAmount.subtract(remainingAmount);
      responses[i] =
          new GeminiOrderStatusResponse(
              i,
              null,
              "Gemini",
              symbol,
              price,
              avgExecutionPrice,
              side,
              type,
              timestamp.toString(),
              timestamp.multiply(new BigDecimal(1000)).longValue(),
              isLive,
              isCancelled,
              wasForced,
              originalAmount,
              remainingAmount,
              executedAmount,
              null);
    }

    return responses;
  }

  @Test
  void adaptTradeHistory() {

    GeminiTradeResponse[] responses = initTradeResponses();
    Trades trades = GeminiAdapters.adaptTradeHistory(responses, SYMBOL);
    assertThat(responses.length).isEqualTo(trades.getTrades().size());

    for (int i = 0; i < responses.length; i++) {
      Trade trade = trades.getTrades().get(i);
      long expectedTimestampMillis =
          responses[i].getTimestamp().multiply(new BigDecimal(1000L)).longValue();
      Order.OrderType expectedOrderType =
          responses[i].getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;

      assertThat(trade.getPrice()).isEqualTo(responses[i].getPrice());
      assertThat(trade.getOriginalAmount()).isEqualTo(responses[i].getAmount());
      assertThat(trade.getCurrencyPair()).isEqualTo(GeminiAdapters.adaptCurrencyPair(SYMBOL));
      assertThat(trade.getTimestamp().getTime()).isEqualTo(expectedTimestampMillis);
      assertThat(trade.getType()).isEqualTo(expectedOrderType);
      assertThat(trade.getId()).isEqualTo(responses[i].getTradeId());
    }
  }

  /**
   * Create 60 {@link GeminiTradeResponse}s. The values increase as array index does. The timestamps
   * increase by 1 second + 1 minute + 1 hour + 1 day in order to test the correct handling of the
   * given timestamp.
   *
   * @return The generated responses.
   */
  private GeminiTradeResponse[] initTradeResponses() {

    GeminiTradeResponse[] responses = new GeminiTradeResponse[60];
    long tradeId = 2000;
    long orderId = 1000;

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal amount = new BigDecimal(1L + i);
      BigDecimal timestamp =
          new BigDecimal("1414658239.41373654")
              .add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      String type = i % 2 == 0 ? "buy" : "sell";
      String tradeIdString = String.valueOf(tradeId++);
      String orderIdString = String.valueOf(orderId++);
      BigDecimal feeAmount = new BigDecimal(0L);
      String feeCurrency = "USD";
      responses[i] =
          new GeminiTradeResponse(
              price,
              amount,
              timestamp,
              MARKET,
              type,
              tradeIdString,
              orderIdString,
              feeAmount,
              feeCurrency);
    }

    return responses;
  }

  @Test
  void adaptLimitOrderFilled() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        GeminiAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/order/example-limit-order-status.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GeminiOrderStatusResponse geminiOrderStatusResponse =
        mapper.readValue(is, GeminiOrderStatusResponse.class);

    Order order = GeminiAdapters.adaptOrder(geminiOrderStatusResponse);

    assertThat(order.getId()).isEqualTo("44375901");
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("400.00"));
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("3"));
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
    assertThat(LimitOrder.class.isAssignableFrom(order.getClass()));
  }

  @Test
  void adaptLimitOrderPartiallyFilled() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        GeminiAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/order/limit-order-partially-filled.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GeminiOrderStatusResponse geminiOrderStatusResponse =
        mapper.readValue(is, GeminiOrderStatusResponse.class);

    Order order = GeminiAdapters.adaptOrder(geminiOrderStatusResponse);

    assertThat(order.getId()).isEqualTo("44375901");
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("400.00"));
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("1"));
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PARTIALLY_FILLED);
    assertThat(LimitOrder.class.isAssignableFrom(order.getClass()));
  }

  @Test
  void adaptLimitOrderUntouched() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        GeminiAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/order/limit-order-untouched.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GeminiOrderStatusResponse geminiOrderStatusResponse =
        mapper.readValue(is, GeminiOrderStatusResponse.class);

    Order order = GeminiAdapters.adaptOrder(geminiOrderStatusResponse);

    assertThat(order.getId()).isEqualTo("44375901");
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("0.00"));
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0"));
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.OPEN);
    assertThat(LimitOrder.class.isAssignableFrom(order.getClass()));
  }

  @Test
  void adaptDynamicTradingFees() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        GeminiAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/account/example-notionalvolume.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GeminiTrailingVolumeResponse trailingVolumeResp =
        mapper.readValue(is, GeminiTrailingVolumeResponse.class);

    List<Instrument> fakeSupportedCurrencyPairs =
        new ArrayList<>(
            Arrays.asList(CurrencyPair.BTC_USD, CurrencyPair.BTC_LTC, CurrencyPair.LTC_XRP));
    Map<Instrument, Fee> dynamicFees =
        GeminiAdapters.AdaptDynamicTradingFees(trailingVolumeResp, fakeSupportedCurrencyPairs);

    assertThat(dynamicFees.size()).isEqualTo(fakeSupportedCurrencyPairs.size());
    for (Instrument pair : fakeSupportedCurrencyPairs) {
      assertThat(dynamicFees.get(pair).getMakerFee()).isEqualTo(new BigDecimal("0.0035"));
      assertThat(dynamicFees.get(pair).getTakerFee()).isEqualTo(new BigDecimal("0.0010"));
    }
  }
}
