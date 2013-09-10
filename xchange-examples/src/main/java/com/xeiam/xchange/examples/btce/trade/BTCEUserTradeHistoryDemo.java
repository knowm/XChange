package com.xeiam.xchange.examples.btce.trade;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.btce.BTCEExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BTCEUserTradeHistoryDemo {

  public static void main(String[] args) {

    // TODO: The APIKey does not have the correct permissions
    Exchange bitstamp = BTCEExamplesUtils.createExchange();
    PollingTradeService tradeService = bitstamp.getPollingTradeService();
    Trades trades = tradeService.getTradeHistory(null, Currencies.BTC, Currencies.USD);
    System.out.println(trades.toString());

  }
}
