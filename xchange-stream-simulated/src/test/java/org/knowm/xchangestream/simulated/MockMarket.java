package org.knowm.xchangestream.simulated;

import static org.knowm.xchange.currency.CurrencyPair.BTC_USD;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;

class MockMarket {

  static void mockMarket(SimulatedStreamingExchange exchange) throws IOException {
    placeMMOrder(exchange, ASK, new BigDecimal(10000), new BigDecimal("200"));
    placeMMOrder(exchange, ASK, new BigDecimal(100), new BigDecimal("0.1"));
    placeMMOrder(exchange, ASK, new BigDecimal(99), new BigDecimal("0.05"));
    placeMMOrder(exchange, ASK, new BigDecimal(99), new BigDecimal("0.25"));
    placeMMOrder(exchange, ASK, new BigDecimal(98), new BigDecimal("0.3"));
    // ----
    placeMMOrder(exchange, BID, new BigDecimal(97), new BigDecimal("0.4"));
    placeMMOrder(exchange, BID, new BigDecimal(96), new BigDecimal("0.25"));
    placeMMOrder(exchange, BID, new BigDecimal(96), new BigDecimal("0.25"));
    placeMMOrder(exchange, BID, new BigDecimal(95), new BigDecimal("0.6"));
    placeMMOrder(exchange, BID, new BigDecimal(94), new BigDecimal("0.7"));
    placeMMOrder(exchange, BID, new BigDecimal(93), new BigDecimal("0.8"));
    placeMMOrder(exchange, BID, new BigDecimal(1), new BigDecimal("1002"));
  }

  static void placeMMOrder(
      SimulatedStreamingExchange exchange, OrderType orderType, BigDecimal price, BigDecimal amount)
      throws IOException {
    exchange
        .getTradeService()
        .placeLimitOrderUnrestricted(
            new LimitOrder.Builder(orderType, BTC_USD)
                .limitPrice(price)
                .originalAmount(amount)
                .build());
  }
}
