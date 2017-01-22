package org.knowm.xchange.examples.btcchina.trade;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaMarketDepthOrder;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetMarketDepthResponse;
import org.knowm.xchange.btcchina.service.rest.BTCChinaTradeServiceRaw;
import org.knowm.xchange.examples.btcchina.BTCChinaExamplesUtils;

/**
 * Demo for {@link BTCChinaTradeServiceRaw#getMarketDepth(Integer, String)}.
 */
public class BTCChinaGetMarketDepthDemo {

  private static Exchange exchange = BTCChinaExamplesUtils.getExchange();
  private static BTCChinaTradeServiceRaw tradeService = (BTCChinaTradeServiceRaw) exchange.getTradeService();

  public static void main(String[] args) throws IOException {

    getMarketDepth(null, null);
    getMarketDepth(10, null);
    getMarketDepth(null, "LTCCNY");
    getMarketDepth(10, "LTCCNY");

  }

  private static void getMarketDepth(Integer limit, String market) throws IOException {

    System.out.println(String.format("limit: %d, market: %s", limit, market));

    BTCChinaGetMarketDepthResponse response = tradeService.getMarketDepth(limit, market);
    System.out.println(response);

    for (BTCChinaMarketDepthOrder o : response.getResult().getMarketDepth().getBids()) {
      System.out.println(String.format("Bid %s,%s", o.getPrice(), o.getAmount()));
    }

    for (BTCChinaMarketDepthOrder o : response.getResult().getMarketDepth().getAsks()) {
      System.out.println(String.format("Ask %s,%s", o.getPrice(), o.getAmount()));
    }
  }

}
