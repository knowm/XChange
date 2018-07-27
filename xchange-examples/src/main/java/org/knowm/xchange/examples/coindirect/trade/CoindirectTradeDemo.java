package org.knowm.xchange.examples.coindirect.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectExchange;
import org.knowm.xchange.coindirect.service.CoindirectTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.coindirect.CoindirectDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

public class CoindirectTradeDemo {
  public static void main(String[] args) throws IOException {
    Exchange exchange = CoindirectDemoUtils.createExchange();

    /* create a data service from the exchange */
    TradeService tradeService = exchange.getTradeService();

    generic(exchange, tradeService);
    raw((CoindirectExchange) exchange, (CoindirectTradeService) tradeService);
  }

  private static void generic(Exchange exchange, TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();

    System.out.println("Got open orders " + openOrders);

    UserTrades userTrades = tradeService.getTradeHistory(null);

    System.out.println("Got user trades " + userTrades);

    LimitOrder limitOrder =
        new LimitOrder.Builder(Order.OrderType.BID, new CurrencyPair("BTC", "ZAR"))
            .limitPrice(new BigDecimal("80000.01"))
            .originalAmount(new BigDecimal("0.001"))
            .build();

    String orderId = tradeService.placeLimitOrder(limitOrder);
    //
    System.out.println("Got orderId " + orderId);

    boolean orderCancelled = tradeService.cancelOrder(orderId);

    System.out.println("Order cancelled " + orderCancelled);

    MarketOrder marketOrder =
        new MarketOrder.Builder(Order.OrderType.BID, new CurrencyPair("BTC", "ZAR"))
            .originalAmount(new BigDecimal("0.001"))
            .build();

    orderId = tradeService.placeMarketOrder(marketOrder);

    System.out.println("Got orderId " + orderId);

    orderCancelled = tradeService.cancelOrder(orderId);

    System.out.println("Order cancelled " + orderCancelled);
  }

  public static void raw(CoindirectExchange exchange, CoindirectTradeService tradeService)
      throws IOException {}
}
