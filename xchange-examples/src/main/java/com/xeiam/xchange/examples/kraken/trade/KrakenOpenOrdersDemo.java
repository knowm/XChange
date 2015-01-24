package com.xeiam.xchange.examples.kraken.trade;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.service.polling.KrakenTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class KrakenOpenOrdersDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = krakenExchange.getPollingTradeService();

    // Get the open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getPollingTradeService();

    // Get the open orders
    Map<String, KrakenOrder> openOrders = tradeService.getKrakenOpenOrders();
    System.out.println(openOrders);

  }
}
