package org.knowm.xchange.examples.btce.trade;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import org.knowm.xchange.btce.v3.service.BTCETradeServiceRaw;
import org.knowm.xchange.btce.v3.service.trade.params.BTCETradeHistoryParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.btce.BTCEExamplesUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;

public class BTCEUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {
    Exchange btce = BTCEExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    TradeService tradeService = exchange.getTradeService();
    try {
      BTCETradeHistoryParams params = new BTCETradeHistoryParams();
      params.setCurrencyPair(CurrencyPair.BTC_USD);
      UserTrades trades = tradeService.getTradeHistory(params);

      System.out.println(trades.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void raw(Exchange exchange) throws IOException {

    BTCETradeServiceRaw tradeService = (BTCETradeServiceRaw) exchange.getTradeService();
    Map<Long, BTCETradeHistoryResult> trades = null;
    try {
      trades = tradeService.getBTCETradeHistory(null, null, null, null, null, null, null, null);
      for (Map.Entry<Long, BTCETradeHistoryResult> entry : trades.entrySet()) {
        System.out.println("ID: " + entry.getKey() + ", Trade:" + entry.getValue());
      }
      System.out.println(trades.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }

}
