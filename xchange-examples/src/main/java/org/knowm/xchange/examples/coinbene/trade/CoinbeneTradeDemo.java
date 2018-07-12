package org.knowm.xchange.examples.coinbene.trade;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.CoinbeneExchange;
import org.knowm.xchange.coinbene.service.CoinbeneTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.coinbene.CoinbeneDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.utils.StreamUtils;

import java.io.IOException;

public class CoinbeneTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CoinbeneDemoUtils.createExchange();

    generic(exchange);
    raw((CoinbeneExchange) exchange);
  }

  public static void generic(Exchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.BTC_USDT;
    TradeService tradeService = exchange.getTradeService();

    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(new DefaultOpenOrdersParamCurrencyPair(pair));
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }

  public static void raw(CoinbeneExchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.BTC_USDT;
    CoinbeneTradeService tradeService = (CoinbeneTradeService) exchange.getTradeService();
    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(pair);
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }
}
