package org.knowm.xchange.coinsph.service.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.knowm.xchange.coinsph.CoinsphExchangeIntegration;
import org.knowm.xchange.coinsph.service.CoinsphTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeServiceIntegration extends CoinsphExchangeIntegration {

  private static final Logger logger = LoggerFactory.getLogger(TradeServiceIntegration.class);
  private CoinsphTradeService tradeService;
  private static final CurrencyPair TEST_CURRENCY_PAIR = new CurrencyPair("BTC", "PHP");
  private static final BigDecimal SMALLEST_BUY_QUANTITY = new BigDecimal("0.00001");

  @BeforeAll
  @Override
  public void setUp() {
    // Call parent's setUp first
    super.setUp();
    // Then set up our specific trade service
    tradeService = (CoinsphTradeService) super.exchange.getTradeService();
  }

  @Test
  void testPlaceMarketOrder() throws IOException {
    final MarketOrder marketOrder = sampleMarketOrder();
    String orderId = tradeService.placeMarketOrder(marketOrder);
    logger.info("Placed market order with ID: {}", orderId);
    // Don't assume success as the account might be out of balance
  }

  @Override
  protected MarketOrder sampleMarketOrder() {
    return new MarketOrder.Builder(Order.OrderType.BID, TEST_CURRENCY_PAIR)
        .originalAmount(SMALLEST_BUY_QUANTITY)
        .build();
  }

  @Test
  void testPlaceLimitOrder() throws IOException {
    final LimitOrder limitOrder = sampleLimitOrder();
    String orderId = tradeService.placeLimitOrder(limitOrder);
    logger.info("Placed limit order with ID: {}", orderId);
    // Don't assume success as the account might be out of balance
  }

  private LimitOrder sampleLimitOrder() throws IOException {
    final BigDecimal limitPrice = limitPriceForCurrencyPair(TEST_CURRENCY_PAIR);
    return new LimitOrder.Builder(Order.OrderType.BID, TEST_CURRENCY_PAIR)
        .originalAmount(SMALLEST_BUY_QUANTITY)
        .limitPrice(limitPrice)
        .build();
  }

  private BigDecimal limitPriceForCurrencyPair(CurrencyPair currencyPair) throws IOException {
    return super.exchange
        .getMarketDataService()
        .getOrderBook(currencyPair)
        .getAsks()
        .get(0)
        .getLimitPrice();
  }

  @Test
  @Disabled("This test requires sufficient balance to place an order")
  void testGetOrderStatus() throws IOException, InterruptedException {
    // Place a market order first
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
}
