package org.knowm.xchange.examples.cryptonit.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.examples.cryptonit.CryptonitDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

public class OrderStatusDemo {

  public static void main(String[] args) throws IOException {

    Exchange cryptonit = CryptonitDemoUtils.createExchange();
    TradeService tradeService = cryptonit.getTradeService();

    System.out.println(tradeService.getOrder(new String[] {"66629"}).toString());
  }
}
