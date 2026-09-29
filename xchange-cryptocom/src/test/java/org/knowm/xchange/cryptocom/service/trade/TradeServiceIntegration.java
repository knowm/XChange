package org.knowm.xchange.cryptocom.service.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.knowm.xchange.cryptocom.CryptoComExchangeIntegration;
import org.knowm.xchange.cryptocom.service.CryptoComTradeService;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class TradeServiceIntegration extends CryptoComExchangeIntegration {

  private static final Logger logger = LoggerFactory.getLogger(TradeServiceIntegration.class);

  private CryptoComTradeService tradeService;

  @BeforeAll
  @Override
  public void setUp() {
    super.setUp();
    tradeService = (CryptoComTradeService) super.exchange.getTradeService();
  }

  @Test
  void getOpenOrders_shouldReturnOpenOrders() throws IOException {
    OpenOrders openOrders = tradeService.getOpenOrders();
    assertThat(openOrders).isNotNull();
    logger.info("Open orders: {}", openOrders.getOpenOrders().size());
  }

  @Test
  @Disabled("Places a real (tiny) order - enable explicitly for manual execution")
  void placeAndCancelLimitOrder_shouldSucceed() throws IOException {
    LimitOrder limitOrder = sampleLimitOrder();
    String orderId = tradeService.placeLimitOrder(limitOrder);
    logger.info("Placed limit order with ID: {}", orderId);
    assertThat(orderId).isNotNull().isNotEmpty();

    boolean cancelled = tradeService.cancelOrder(orderId);
    logger.info("Cancelled order {}: {}", orderId, cancelled);
    assertThat(cancelled).isTrue();
  }

  private LimitOrder sampleLimitOrder() throws IOException {
    // Far below market price so it rests on the book instead of filling immediately.
    BigDecimal bestBid =
        super.exchange
            .getMarketDataService()
            .getOrderBook(TEST_CURRENCY_PAIR)
            .getBids()
            .get(0)
            .getLimitPrice();
    BigDecimal limitPrice = bestBid.multiply(new BigDecimal("0.5"));
    return new LimitOrder.Builder(Order.OrderType.BID, TEST_CURRENCY_PAIR)
        .originalAmount(SMALLEST_BUY_QUANTITY)
        .limitPrice(limitPrice)
        .build();
  }
}
