package org.knowm.xchange.examples.anx.v2.marketdata;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTrade;
import org.knowm.xchange.anx.v2.service.ANXMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class ANXTradesDemo {

  private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    MarketDataService marketDataService = anx.getMarketDataService();

    generic(marketDataService);
    raw((ANXMarketDataServiceRaw) marketDataService);
  }

  public static void generic(MarketDataService marketDataService) throws IOException {

    Trades trades =
        marketDataService.getTrades(
            CurrencyPair.BTC_USD, System.currentTimeMillis() - DAY_IN_MILLIS);
    System.out.println(trades);
  }

  public static void raw(ANXMarketDataServiceRaw marketDataServiceRaw) throws IOException {

    List<ANXTrade> trades =
        marketDataServiceRaw.getANXTrades(
            CurrencyPair.BTC_USD, System.currentTimeMillis() - DAY_IN_MILLIS);
    System.out.println(trades);
  }
}
