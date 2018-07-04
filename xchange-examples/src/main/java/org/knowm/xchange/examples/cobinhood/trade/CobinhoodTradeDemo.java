package org.knowm.xchange.examples.cobinhood.trade;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.CobinhoodExchange;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOpenOrdersParams;
import org.knowm.xchange.cobinhood.service.CobinhoodTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.cobinhood.CobinhoodDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.StreamUtils;

import java.io.IOException;

public class CobinhoodTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CobinhoodDemoUtils.createExchange();

    generic(exchange);
    raw((CobinhoodExchange) exchange);
  }

  public static void generic(Exchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.BTC_USDT;
    TradeService tradeService = exchange.getTradeService();

    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(new CobinhoodOpenOrdersParams(pair));
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }

  public static void raw(CobinhoodExchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.BTC_USDT;
    CobinhoodTradeService tradeService = (CobinhoodTradeService) exchange.getTradeService();
    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(pair);
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }
}
