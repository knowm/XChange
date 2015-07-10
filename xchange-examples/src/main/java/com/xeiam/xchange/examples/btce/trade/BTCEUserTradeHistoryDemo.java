package com.xeiam.xchange.examples.btce.trade;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.btce.v3.service.polling.BTCETradeServiceRaw;
import com.xeiam.xchange.btce.v3.service.polling.trade.params.BTCETradeHistoryParams;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.examples.btce.BTCEExamplesUtils;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class BTCEUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {
    Exchange btce = BTCEExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    PollingTradeService tradeService = exchange.getPollingTradeService();
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

    BTCETradeServiceRaw tradeService = (BTCETradeServiceRaw) exchange.getPollingTradeService();
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
