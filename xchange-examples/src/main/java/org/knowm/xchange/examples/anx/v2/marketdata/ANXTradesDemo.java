package org.knowm.xchange.examples.anx.v2.marketdata;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTrade;
import org.knowm.xchange.anx.v2.service.polling.ANXMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class ANXTradesDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    PollingMarketDataService marketDataService = anx.getPollingMarketDataService();

    generic(marketDataService);
    raw((ANXMarketDataServiceRaw) marketDataService);
  }

  private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

  public static void generic(PollingMarketDataService marketDataService) throws IOException {

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD, System.currentTimeMillis() - DAY_IN_MILLIS);
    System.out.println(trades);
  }

  public static void raw(ANXMarketDataServiceRaw marketDataServiceRaw) throws IOException {

    List<ANXTrade> trades = marketDataServiceRaw.getANXTrades(CurrencyPair.BTC_USD, System.currentTimeMillis() - DAY_IN_MILLIS);
    System.out.println(trades);
  }
}
