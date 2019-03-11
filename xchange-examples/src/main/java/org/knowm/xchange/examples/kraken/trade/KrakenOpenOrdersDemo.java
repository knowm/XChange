package org.knowm.xchange.examples.kraken.trade;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.service.KrakenTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class KrakenOpenOrdersDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    TradeService tradeService = krakenExchange.getTradeService();

    // Get the open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getTradeService();

    // Get the open orders
    Map<String, KrakenOrder> openOrders = tradeService.getKrakenOpenOrders();
    System.out.println(openOrders);
  }
}
