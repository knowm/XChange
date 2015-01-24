package com.xeiam.xchange.examples.kraken.trade;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;
import com.xeiam.xchange.kraken.service.polling.KrakenTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class KrakenTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = krakenExchange.getPollingTradeService();

    // Get the trade history
    Trades trades = tradeService.getTradeHistory();
    System.out.println(trades.toString());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getPollingTradeService();

    // Get the trade history
    Map<String, KrakenTrade> trades = tradeService.getKrakenTradeHistory();
    System.out.println(trades);

  }
}
