package org.knowm.xchange.examples.anx.v2.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

/** Test requesting all open orders at MtGox */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private trading functionality (authentication)
    TradeService tradeService = anx.getTradeService();

    Trades trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    for (Trade trade : trades.getTrades()) {
      System.out.println(trade);
    }
  }
}
