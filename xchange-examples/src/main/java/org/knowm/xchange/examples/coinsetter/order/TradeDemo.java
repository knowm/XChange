package org.knowm.xchange.examples.coinsetter.order;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.coinsetter.CoinsetterExamplesUtils;
import org.knowm.xchange.service.polling.trade.PollingTradeService;

/**
 * Generic trade service demonstration.
 */
public class TradeDemo {

  private static final Logger log = LoggerFactory.getLogger(TradeDemo.class);

  public static void main(String[] args) throws IOException {

    String username = args[0];
    String password = args[1];
    String ipAddress = args[2];

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange(username, password, ipAddress);
    PollingTradeService tradeService = coinsetter.getPollingTradeService();

    String orderId = tradeService
        .placeLimitOrder(new LimitOrder(OrderType.BID, new BigDecimal("0.01"), CurrencyPair.BTC_USD, null, null, new BigDecimal("0.01")));
    log.info("limit order id: {}", orderId);

    OpenOrders openOrders = tradeService.getOpenOrders();
    log.info("open orders: {}", openOrders);

    boolean cancelled = tradeService.cancelOrder(orderId);
    log.info("order {} canceling result: {}", orderId, cancelled);

    ((CoinsetterTradeService) tradeService).logout();
  }

}
