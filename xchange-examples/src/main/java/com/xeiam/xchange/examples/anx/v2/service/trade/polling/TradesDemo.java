package com.xeiam.xchange.examples.anx.v2.service.trade.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

import java.io.IOException;

/**
 * Test requesting all open orders at MtGox
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = anx.getPollingTradeService();

    Trades trades = tradeService.getTradeHistory();
    for (Trade trade : trades.getTrades()) {
      System.out.println(trade);
    }
  }

}
