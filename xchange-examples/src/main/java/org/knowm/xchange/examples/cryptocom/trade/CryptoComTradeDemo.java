package org.knowm.xchange.examples.cryptocom.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptocom.CryptoComAdapters;
import org.knowm.xchange.cryptocom.service.CryptoComTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.cryptocom.CryptoComDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderParamId;
import org.knowm.xchange.utils.StreamUtils;

public class CryptoComTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CryptoComDemoUtils.createExchange();

    generic(exchange);
    raw((CryptoComTradeServiceRaw) exchange.getTradeService());
  }

  public static void generic(Exchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.BTC_USDT;
    TradeService tradeService = exchange.getTradeService();

    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders();
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println("Open order: " + order);

      // Cancel it
      tradeService.cancelOrder(new DefaultCancelOrderParamId(order.getId()));
    } else {
      System.out.println("No open orders for " + pair);
    }
  }

  public static void raw(CryptoComTradeServiceRaw tradeService) throws IOException {

    String instrumentName = CryptoComAdapters.toInstrumentName(CurrencyPair.BTC_USDT);
    System.out.println(
        "Open orders: " + tradeService.getCryptoComOpenOrders(instrumentName).size());
  }
}
