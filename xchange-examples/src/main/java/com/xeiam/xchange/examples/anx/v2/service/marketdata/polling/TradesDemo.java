package com.xeiam.xchange.examples.anx.v2.service.marketdata.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTrade;
import com.xeiam.xchange.anx.v2.service.polling.ANXMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class TradesDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    PollingMarketDataService marketDataService = anx.getPollingMarketDataService();

    generic(marketDataService);
    raw((ANXMarketDataServiceRaw) marketDataService);
  }

  private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

  public static void generic(PollingMarketDataService marketDataService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD, System.currentTimeMillis() - DAY_IN_MILLIS);
    System.out.println(trades);
  }

  public static void raw(ANXMarketDataServiceRaw marketDataServiceRaw) throws IOException {

    List<ANXTrade> trades = marketDataServiceRaw.getANXTrades(CurrencyPair.BTC_USD, System.currentTimeMillis() - DAY_IN_MILLIS);
    System.out.println(trades);
  }
}
