package org.knowm.xchange.examples.binance.trade;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.binance.BinanceDemoUtils;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.*;
import org.knowm.xchange.utils.StreamUtils;

public class BinanceTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BinanceDemoUtils.createExchange();

    generic(exchange);
    raw((BinanceExchange) exchange);
  }

  public static void generic(Exchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.EOS_ETH;
    TradeService tradeService = exchange.getTradeService();

    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(new DefaultOpenOrdersParamCurrencyPair(pair));
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }

    // Get orders using params
    if (order != null) {
      Class queryParamClass = exchange.getTradeService().getRequiredOrderQueryParamClass();
      OrderQueryParams queryParams = new DefaultQueryOrderParam(order.getId());
      if (queryParamClass == OrderQueryParamCurrencyPair.class) {
        queryParams = new DefaultQueryOrderParamCurrencyPair(pair, order.getId());
      } else if (queryParamClass == OrderQueryParamInstrument.class) {
        Instrument pairAsInstrument = pair;
        queryParams = new DefaultQueryOrderParamInstrument(pairAsInstrument, order.getId());
      }
      Collection<Order> ordersWithId = exchange.getTradeService().getOrder(queryParams);
      if (ordersWithId != null) {
        System.out.println(ordersWithId);
      }
    }

    // Cancel order
    if (order != null) {
      List<Class> classList =
          Arrays.asList(exchange.getTradeService().getRequiredCancelOrderParamClasses());

      CancelOrderParams cancelParam = new DefaultCancelOrderParamId(order.getId());
      if (classList.contains(CancelOrderByCurrencyPair.class)
          && classList.contains(CancelOrderByIdParams.class)) {
        cancelParam = new DefaultCancelOrderByCurrencyPairAndIdParams(pair, order.getId());
      }

      exchange.getTradeService().cancelOrder(cancelParam);
    }
  }

  public static void raw(BinanceExchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.EOS_ETH;
    BinanceTradeService tradeService = (BinanceTradeService) exchange.getTradeService();
    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(pair);
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }
}
