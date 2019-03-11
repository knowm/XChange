package org.knowm.xchange.examples.wex.trade;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.wex.WexExamplesUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.wex.v3.dto.trade.WexTradeHistoryResult;
import org.knowm.xchange.wex.v3.service.WexTradeServiceRaw;
import org.knowm.xchange.wex.v3.service.trade.params.WexTradeHistoryParams;

public class WexUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {
    Exchange btce = WexExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    TradeService tradeService = exchange.getTradeService();
    try {
      WexTradeHistoryParams params = new WexTradeHistoryParams();
      params.setCurrencyPair(CurrencyPair.BTC_USD);
      UserTrades trades = tradeService.getTradeHistory(params);

      System.out.println(trades.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void raw(Exchange exchange) throws IOException {

    WexTradeServiceRaw tradeService = (WexTradeServiceRaw) exchange.getTradeService();
    Map<Long, WexTradeHistoryResult> trades = null;
    try {
      trades = tradeService.getBTCETradeHistory(null, null, null, null, null, null, null, null);
      for (Map.Entry<Long, WexTradeHistoryResult> entry : trades.entrySet()) {
        System.out.println("ID: " + entry.getKey() + ", Trade:" + entry.getValue());
      }
      System.out.println(trades.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }
}
