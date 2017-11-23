package org.knowm.xchange.examples.lakebtc.trade;

import java.io.IOException;
import java.util.Arrays;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCTradeResponse;
import org.knowm.xchange.lakebtc.service.LakeBTCTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class LakeBTCTradeHistoryDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {

    TradeService tradeService = lakebtcExchange.getTradeService();

    // Get the trade history
    Trades trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println(trades);

  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCTradeServiceRaw tradeService = (LakeBTCTradeServiceRaw) lakeBtcExchange.getTradeService();

    // Get the trade history
    LakeBTCTradeResponse[] trades = tradeService.getLakeBTCTradeHistory(0);
    System.out.println(Arrays.toString(trades));

  }
}
