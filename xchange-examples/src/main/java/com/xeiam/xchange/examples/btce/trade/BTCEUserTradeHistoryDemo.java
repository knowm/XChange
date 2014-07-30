package com.xeiam.xchange.examples.btce.trade;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.btce.v3.service.polling.BTCETradeServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.btce.BTCEExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BTCEUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    // TODO: The APIKey does not have the correct permissions
    Exchange btce = BTCEExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    PollingTradeService tradeService = exchange.getPollingTradeService();
    Trades trades = null;
    try {
      trades = tradeService.getTradeHistory(null, Currencies.BTC, Currencies.USD);
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
